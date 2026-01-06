package org.intecbrussel.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CourseResponse {
    private long id;
    private String title;
    private String description;
    private String instructorName;
    private List<String> studentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
