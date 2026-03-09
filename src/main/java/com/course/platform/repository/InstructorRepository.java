package com.course.platform.repository;

import com.course.platform.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByEmail(String email);

    List<Instructor> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT i
        FROM Instructor i
        JOIN FETCH i.courses
        WHERE i.id = :id
    """)
    Instructor findInstructorWithCourses(Long id);

    @Query("""
        SELECT i
        FROM Instructor i
        WHERE SIZE(i.courses) > :courseCount
    """)
    List<Instructor> findInstructorsWithMoreThanXCourses(int courseCount);
}
