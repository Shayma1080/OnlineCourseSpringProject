package org.intecbrussel.mapping;

import org.intecbrussel.dto.EnrollmentResponse;
import org.intecbrussel.model.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setStudentName(enrollment.getStudent().getFirstName());
        response.setCourseName(enrollment.getCourse().getTitle());
        response.setCreatedAt(enrollment.getEnrolledAt());
        return response;
    }
}
