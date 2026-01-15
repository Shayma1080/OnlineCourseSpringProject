package org.intecbrussel.service;

import jakarta.transaction.Transactional;
import org.intecbrussel.dto.EnrollmentResponse;
import org.intecbrussel.exception.DupblicateEnrollmentException;
import org.intecbrussel.exception.ResourceNotFoundException;
import org.intecbrussel.exception.UnauthorizedActionException;
import org.intecbrussel.mapping.EnrollmentMapper;
import org.intecbrussel.model.Course;
import org.intecbrussel.model.Enrollment;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.CourseRepository;
import org.intecbrussel.repository.EnrollmentRepository;
import org.intecbrussel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private final EnrollmentRepository enrollmentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public EnrollmentResponse enrollStudent(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if(enrollmentRepository.existsByStudentAndCourse(student,course)){
            throw new DupblicateEnrollmentException("Student is already enrolled in this course");

        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toResponse(enrollment);
    }

    @Transactional
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }

    @Transactional
    public List<EnrollmentResponse> getEnrollmentForStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return enrollmentRepository.findByStudent(student)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }

    @Transactional
    public List<EnrollmentResponse> getEnrollmentInstructor(Long instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        return enrollmentRepository.findByCourseInstructor(instructor)
                .stream()
                .map(EnrollmentMapper::toResponse)
                .toList();
    }
 @Transactional
    public void cancelEnrollment(Long enrollmentId,Long userId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!user.getRole().equals(Role.ADMIN) && !enrollment.getStudent().getId().equals(userId)){
            throw new UnauthorizedActionException("Insufficient rights to cancel enrollment");
        }


        enrollmentRepository.delete(enrollment);
    }
}
