package org.intecbrussel.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponse {
    private long id;
    private String studentName;
    private String courseName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
