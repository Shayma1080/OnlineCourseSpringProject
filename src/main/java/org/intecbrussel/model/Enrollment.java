package org.intecbrussel.model;

import jakarta.persistence.*;
import jakarta.transaction.Status;

import java.time.LocalDateTime;

@Entity
public class Enrollment {
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

    @Enumerated(EnumType.STRING)
    private Status status;


}
