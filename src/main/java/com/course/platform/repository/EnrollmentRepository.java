package com.course.platform.repository;

import com.course.platform.model.Course;
import com.course.platform.model.Enrollment;
import com.course.platform.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByCourseId(Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
        SELECT COUNT(e)
        FROM Enrollment e
        WHERE e.course.id = :courseId
    """)
    long countStudentsInCourse(Long courseId);

    @Query("""
        SELECT e.student
        FROM Enrollment e
        WHERE e.course.id = :courseId
    """)
    List<Student> findStudentsByCourse(Long courseId);

    @Query("""
        SELECT e.course
        FROM Enrollment e
        WHERE e.student.id = :studentId
    """)
    List<Course> findCoursesByStudent(Long studentId);
}