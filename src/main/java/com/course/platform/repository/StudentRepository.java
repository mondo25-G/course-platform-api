package com.course.platform.repository;

import com.course.platform.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);

    List<Student> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT s
        FROM Student s
        JOIN FETCH s.enrollments e
        WHERE s.id = :studentId
    """)
    Student findStudentWithEnrollments(Long studentId);

    @Query("""
        SELECT s
        FROM Student s
        WHERE SIZE(s.enrollments) > :courseCount
    """)
    List<Student> findStudentsWithMoreThanXEnrollments(int courseCount);
}
