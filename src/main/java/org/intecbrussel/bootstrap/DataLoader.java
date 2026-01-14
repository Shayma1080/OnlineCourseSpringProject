package org.intecbrussel.bootstrap;


import lombok.RequiredArgsConstructor;
import org.intecbrussel.model.*;
import org.intecbrussel.repository.CourseRepository;
import org.intecbrussel.repository.EnrollmentRepository;
import org.intecbrussel.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        // 1️⃣ Alleen seeden als DB leeg is
        if (userRepository.count() > 0) {
            return;
        }

        // ---------------- USERS ----------------


        User admin = new User();
        admin.setEmail("admin@intec.com");
        admin.setFirstName("AdminUser");
        admin.setLastName("SuperAdmin");
        admin.setUsername(admin.getFirstName() + " " + admin.getLastName());
        admin.setPassword(passwordEncoder.encode("adminpassword"));
        admin.setRole(Role.ADMIN); // hier belangrijk!
        userRepository.save(admin);


        User instructor1 = new User(
                null,
                "Instructor1",
                "One",
                "instructor1",
                "instructor1@intec.com",
                passwordEncoder.encode("instructor123"),
                Role.INSTRUCTOR
        );

        User instructor2 = new User(
                null,
                "Instructor2",
                "Two",
                "instructor2",
                "instructor2@intec.com",
                passwordEncoder.encode("instructor123"),
                Role.INSTRUCTOR
        );

        User student1 = new User(
                null,
                "Student1",
                "One",
                "student1",
                "student1@intec.com",
                passwordEncoder.encode("student123"),
                Role.STUDENT
        );

        User student2 = new User(
                null,
                "Student2",
                "Two",
                "student2",
                "student2@intec.com",
                passwordEncoder.encode("student123"),
                Role.STUDENT
        );

        User student3 = new User(
                null,
                "Student3",
                "Three",
                "student3",
                "student3@intec.com",
                passwordEncoder.encode("student123"),
                Role.STUDENT
        );

        userRepository.saveAll(List.of(admin, instructor1, instructor2, student1, student2, student3));

        // ---------------- COURSES ----------------
        Course java = new Course();
        java.setTitle("Java Fundamentals");
        java.setDescription("Introductie tot Java");
        java.setInstructor(instructor1);
        java.setCreatedAt(LocalDateTime.now());
        java.setUpdatedAt(LocalDateTime.now());

        Course spring = new Course();
        spring.setTitle("Spring Boot");
        spring.setDescription("Spring Boot & REST APIs");
        spring.setInstructor(instructor1);
        spring.setCreatedAt(LocalDateTime.now());
        spring.setUpdatedAt(LocalDateTime.now());

        Course database = new Course();
        database.setTitle("Databases");
        database.setDescription("SQL & JPA");
        database.setInstructor(instructor2);
        database.setCreatedAt(LocalDateTime.now());
        database.setUpdatedAt(LocalDateTime.now());

        courseRepository.saveAll(List.of(java, spring, database));

        // ---------------- ENROLLMENTS ----------------
        Enrollment e1 = Enrollment.builder()
                .student(student1)
                .course(java)
                .enrolledAt(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        Enrollment e2 = Enrollment.builder()
                .student(student1)
                .course(spring)
                .enrolledAt(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        Enrollment e3 = Enrollment.builder()
                .student(student2)
                .course(database)
                .enrolledAt(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        Enrollment e4 = Enrollment.builder()
                .student(student3)
                .course(java)
                .enrolledAt(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        Enrollment e5 = Enrollment.builder()
                .student(student3)
                .course(spring)
                .enrolledAt(LocalDateTime.now())
                .status(Status.IN_PROGRESS)
                .build();

        enrollmentRepository.saveAll(List.of(e1, e2, e3, e4, e5));

        System.out.println("✅ Database succesvol geseed!");
    }
}
