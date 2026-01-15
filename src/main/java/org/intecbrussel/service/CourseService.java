package org.intecbrussel.service;

import jakarta.transaction.Transactional;
import org.intecbrussel.dto.CourseRequest;
import org.intecbrussel.dto.CourseResponse;
import org.intecbrussel.exception.ResourceNotFoundException;
import org.intecbrussel.exception.UnauthorizedActionException;
import org.intecbrussel.mapping.CourseMapper;
import org.intecbrussel.model.Course;
import org.intecbrussel.model.Role;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.CourseRepository;
import org.intecbrussel.repository.EnrollmentRepository;
import org.intecbrussel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public CourseResponse createCourse(Long instructorId, CourseRequest request) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(()-> new ResourceNotFoundException("instructor not found"));
        if(instructor.getRole() != Role.INSTRUCTOR && instructor.getRole() != Role.ADMIN) {
            throw new UnauthorizedActionException("No rights to create courses");
        }
        Course course = CourseMapper.toEntity(request,instructor);
        courseRepository.save(course);
        return CourseMapper.toResponse(course);
    }
    @Transactional
    public CourseResponse updateCourse(Long courseId, CourseRequest request, Long instructorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new ResourceNotFoundException("course not found"));

        if(course.getInstructor() == null){
            throw new UnauthorizedActionException("Course has no instructor, cannot update");
        }

        if(!course.getInstructor().getId().equals(instructorId)) {
            throw new UnauthorizedActionException("You can't update the course");
        }

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        courseRepository.save(course);
        return CourseMapper.toResponse(course);

    }

    @Transactional
    public void deleteCourse(Long courseId, Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(()-> new ResourceNotFoundException("admin not found"));

        if(!admin.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedActionException("You can't delete the course");
        }

        courseRepository.deleteById(courseId);
    }

    @Transactional
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    @Transactional
    public CourseResponse getCourseById(Long courseId) {
        Course course = courseRepository.getCourseById(courseId);
        return CourseMapper.toResponse(course);
    }
}
