package org.intecbrussel.controller;

import org.intecbrussel.dto.EnrollmentResponse;
import org.intecbrussel.model.Enrollment;
import org.intecbrussel.service.CourseService;
import org.intecbrussel.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/courses/{studentId}/enroll")
    public void enrollCourse(@PathVariable Long studentId,@RequestParam Long courseId){
        enrollmentService.enrollStudent(studentId, courseId);
    }

    @GetMapping("/enrollments/me")
    public List<EnrollmentResponse> getEnrollmentStudent(@RequestParam Long studentId){
        return enrollmentService.getEnrollmentForStudent(studentId);
    }

    @GetMapping("/instructor/enrollments")
    public List<EnrollmentResponse> getEnrollmentsInstructors(@RequestParam Long instructorId){
        return enrollmentService.getEnrollmentInstructor(instructorId);
    }

    @GetMapping("/admin/enrollments")
    public List<EnrollmentResponse> getEnrollmentsAdmin(){
        return enrollmentService.getAllEnrollments();
    }

    @DeleteMapping("/enrollments/{enrollmentId}")
    public void deleteEnrollment(@PathVariable Long enrollmentId, @RequestParam Long userId){
        enrollmentService.cancelEnrollment(enrollmentId,userId);
    }
}
