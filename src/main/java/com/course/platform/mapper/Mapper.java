package com.course.platform.mapper;

import com.course.platform.dto.*;
import com.course.platform.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static InstructorDTO toInstructorDTO(Instructor instructor) {
        List<CourseDTO> courses = instructor.getCourses().stream()
                .map(Mapper::toCourseDTO)
                .collect(Collectors.toList());

        return new InstructorDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                courses
        );
    }

    public static CourseDTO toCourseDTO(Course course) {
        List<LessonDTO> lessons = course.getLessons().stream()
                .map(Mapper::toLessonDTO)
                .collect(Collectors.toList());

        return new CourseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructor() != null ? course.getInstructor().getId() : null,
                lessons,
                course.getEnrollments() != null ? course.getEnrollments().size() : 0
        );
    }

    public static LessonDTO toLessonDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getVideoUrl(),
                lesson.getPosition()
        );
    }

    public static StudentDTO toStudentDTO(Student student) {
        List<CourseDTO> courses = student.getEnrollments().stream()
                .map(enrollment -> toCourseDTO(enrollment.getCourse()))
                .collect(Collectors.toList());

        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                courses
        );
    }

    public static EnrollmentDTO toEnrollmentDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getStudent().getName(),
                enrollment.getEnrolledAt()
        );
    }
}
