package com.course.platform.service;

import com.course.platform.dto.LessonDTO;
import com.course.platform.dto.light.LessonLightDTO;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.mapper.Mapper;
import com.course.platform.model.Course;
import com.course.platform.model.Lesson;
import com.course.platform.repository.CourseRepository;
import com.course.platform.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    // Get all lessons
    public List<LessonDTO> getAllLessons() {
        return lessonRepository.findAll().stream()
                .map(Mapper::toLessonDTO)
                .collect(Collectors.toList());
    }

    // Get lesson by ID
    public LessonDTO getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id " + id + " not found"));
        return Mapper.toLessonDTO(lesson);
    }

    // Create a lesson under a course
    public LessonDTO createLesson(Long courseId, LessonLightDTO dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + courseId + " not found"));

        Lesson lesson = new Lesson();
        lesson.setTitle(dto.title());
        lesson.setVideoUrl(dto.videoUrl());
        lesson.setPosition(dto.position());

        // Maintain bidirectional relationship
        lesson.setCourse(course);
        course.getLessons().add(lesson);

        Lesson saved = lessonRepository.save(lesson);
        return Mapper.toLessonDTO(saved);
    }

    // Update a lesson
    public LessonDTO updateLesson(Long lessonId, LessonLightDTO dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id " + lessonId + " not found"));

        lesson.setTitle(dto.title());
        lesson.setVideoUrl(dto.videoUrl());
        lesson.setPosition(dto.position());

        // Transactional context persists changes automatically
        return Mapper.toLessonDTO(lesson);
    }

    // Delete a lesson
    public void deleteLesson(Long lessonId) {
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " not found");
        }
        lessonRepository.deleteById(lessonId);
    }
}