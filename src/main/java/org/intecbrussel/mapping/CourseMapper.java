package org.intecbrussel.mapping;

import org.intecbrussel.dto.CourseRequest;
import org.intecbrussel.model.Course;
import org.intecbrussel.dto.CourseResponse;
import org.intecbrussel.model.User;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class CourseMapper {

    public static CourseResponse toResponse(Course course){
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
         if(course.getInstructor() != null){
             response.setInstructorName(course.getInstructor().getEmail());
         }else{
             response.setInstructorName("No instructor");
         }
         response.setStudentName(
                 course.getEnrollments() != null?
                         course.getEnrollments().stream()
                                 .map(e-> e.getStudent().getEmail())
                                 .collect(Collectors.toList())
                         : new ArrayList<>()

         );
//        response.setInstructorName(course.getInstructor().getEmail());
//        response.setStudentName(
//        course.getEnrollments().stream()
//                .map(e -> e.getStudent().getEmail())
//                .collect(Collectors.toList())
//        );

        return response;
    }

    public static Course toEntity(CourseRequest request, User Instructor){
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setInstructor(Instructor);
        return course;
    }
}
