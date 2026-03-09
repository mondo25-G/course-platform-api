package com.course.platform.repository;

import com.course.platform.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);

    List<Lesson> findByCourseIdOrderByPositionAsc(Long courseId);

    @Query("""
        SELECT l
        FROM Lesson l
        WHERE l.course.id = :courseId
        AND l.position > :position
    """)
    List<Lesson> findLessonsAfterPosition(Long courseId, int position);

}