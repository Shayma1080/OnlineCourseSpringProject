package org.intecbrussel.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enrollment { // inschrijving
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;


    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING) // vertelt aan JPA de enumwaarde o te slaan
    private Status status;


}
