package com.course.platform.service;

import com.course.platform.dto.CourseDTO;
import com.course.platform.dto.light.CourseLightDTO;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.mapper.Mapper;
import com.course.platform.model.Course;
import com.course.platform.model.Instructor;
import com.course.platform.repository.CourseRepository;
import com.course.platform.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(Mapper::toCourseDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findCourseWithLessons(id).orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " not found"));
        return Mapper.toCourseDTO(course);
    }

    public CourseDTO createCourse(Long instructorId, CourseLightDTO dto) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instructor with id " + instructorId + " not found"));

        Course course = new Course();
        course.setTitle(dto.title());
        course.setDescription(dto.description());

        // Maintain bidirectional relationship
        course.setInstructor(instructor);
        instructor.getCourses().add(course);

        Course savedCourse = courseRepository.save(course);
        return Mapper.toCourseDTO(savedCourse);
    }


    public CourseDTO updateCourse(Long courseId, CourseLightDTO dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + courseId + " not found"));

        course.setTitle(dto.title());
        course.setDescription(dto.description());

        // Transactional context automatically persists the changes
        return Mapper.toCourseDTO(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course with id " + id + " not found");
        }
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> getMostPopularCourses() {
        return courseRepository.findMostPopularCourses().stream()
                .map(Mapper::toCourseDTO)
                .collect(Collectors.toList());
    }
}
