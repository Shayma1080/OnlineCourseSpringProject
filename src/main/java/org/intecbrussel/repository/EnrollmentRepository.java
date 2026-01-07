package org.intecbrussel.repository;

import org.intecbrussel.model.Enrollment;
import org.intecbrussel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    List<Enrollment> findByStudent(User student);
    List<Enrollment> findByCourseInstructor(User instructor);

}
