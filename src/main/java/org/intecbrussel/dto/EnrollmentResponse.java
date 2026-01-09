package org.intecbrussel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private long id;
    private String studentName;
    private String courseName;
    private LocalDateTime createdAt;
   // private LocalDateTime updatedAt;
}
