package org.intecbrussel.mapping;

import org.intecbrussel.dto.EnrollmentResponse;
import org.intecbrussel.model.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setStudentName(response.getStudentName());
        response.setCourseName(response.getCourseName());
        response.setCreatedAt(response.getCreatedAt());
        response.setUpdatedAt(response.getUpdatedAt());
        return response;
    }
}
