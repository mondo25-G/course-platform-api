package com.course.platform.repository;

import com.course.platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructorId(Long instructorId);

    List<Course> findByTitleContainingIgnoreCase(String keyword);

    @Query("""
        SELECT c
        FROM Course c
        JOIN FETCH c.lessons
        WHERE c.id = :id
    """)
    Course findCourseWithLessons(Long id);

    @Query("""
        SELECT c
        FROM Course c
        JOIN FETCH c.enrollments e
        WHERE c.id = :id
    """)
    Course findCourseWithEnrollments(Long id);

    @Query("""
        SELECT c
        FROM Course c
        WHERE SIZE(c.lessons) > :lessonCount
    """)
    List<Course> findCoursesWithMoreThanXLessons(int lessonCount);

    @Query("""
    SELECT c
    FROM Course c
    JOIN c.enrollments e
    GROUP BY c
    ORDER BY COUNT(e) DESC
""")
    List<Course> findMostPopularCourses();

}
