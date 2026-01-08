package org.intecbrussel.controller;

import org.intecbrussel.model.Enrollment;
import org.intecbrussel.service.CourseService;
import org.intecbrussel.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/courses/{studentId}/enroll")
    public void enrollCourse(@PathVariable Long studentId, Long courseId){
        enrollmentService.enrollStudent(studentId, courseId);
    }

    @GetMapping("/enrollments/me")
    public void getEnrollmentStudent(@PathVariable Long studentId){
        enrollmentService.getEnrollmentForStudent(studentId);
    }

    @GetMapping("/instructor/enrollments")
    public void getEnrollmentsInstructor(@PathVariable Long instructorId){
        enrollmentService.getEnrollmentForStudent(instructorId);
    }

    @GetMapping("/admin/enrollments")
    public void getEnrollmentsAdmin(){
        enrollmentService.getAllEnrollments();
    }

    @DeleteMapping("/enrollments/{studentId}")
    public void deleteEnrollment(@PathVariable Long studentId){
        enrollmentService.cancelEnrollment(studentId);
    }
}
