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

    @GetMapping
    public List<CourseResponse> getCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @PostMapping
    public CourseResponse createCourse(@RequestBody CourseRequest request, Long id){
        return courseService.createCourse(request,id);
    }

    @PutMapping("/{id}")
    public CourseResponse updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest request, Long instructorId){
        return courseService.updateCourse(courseId,request,instructorId);
    }

    @DeleteMapping("/{id}")
    public void cancelCourse(@PathVariable Long courseId, Long adminId){
        courseService.deleteCourse(courseId,adminId);
    }

}
