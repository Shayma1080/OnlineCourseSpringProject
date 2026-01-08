package org.intecbrussel.controller;

import org.intecbrussel.dto.CourseRequest;
import org.intecbrussel.dto.CourseResponse;
import org.intecbrussel.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @PostMapping("/instructorId")
    public CourseResponse createCourse( @RequestParam Long instructorId, @RequestBody CourseRequest request){
        return courseService.createCourse(instructorId, request);
    }

    @PutMapping("/{courseId}")
    public CourseResponse updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest request, @RequestParam Long instructorId){
        return courseService.updateCourse(courseId,request,instructorId);
    }

    @DeleteMapping("/{courseId}")
    public void cancelCourse(@PathVariable Long courseId, @RequestParam Long adminId){
        courseService.deleteCourse(courseId,adminId);
    }

}
