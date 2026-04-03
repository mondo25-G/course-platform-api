package com.course.platform.unit;

import com.course.platform.dto.CourseDTO;
import com.course.platform.dto.light.CourseLightDTO;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.model.Course;
import com.course.platform.model.Instructor;
import com.course.platform.repository.CourseRepository;
import com.course.platform.repository.InstructorRepository;
import com.course.platform.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private CourseService courseService;

    @Captor
    private ArgumentCaptor<Course> courseCaptor;

    private Instructor instructor;

    @BeforeEach
    void setup() {
        instructor = new Instructor("John Doe", "john@example.com");
    }

    // =========================
    // getAllCourses
    // =========================
    @Test
    void getAllCourses_shouldReturnListOfCourseDTOs() {
        Course course1 = new Course("Java Basics", "Intro to Java");
        Course course2 = new Course("Spring Boot", "Spring Boot Guide");
        List<Course> courses = List.of(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        List<CourseDTO> result = courseService.getAllCourses();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CourseDTO::title)
                .containsExactly("Java Basics", "Spring Boot");
    }

    @Test
    void getAllCourses_shouldReturnEmptyListWhenNoCourses() {
        when(courseRepository.findAll()).thenReturn(List.of());

        List<CourseDTO> result = courseService.getAllCourses();

        assertThat(result).isEmpty();
    }

    // =========================
    // getCourseById
    // =========================
    @Test
    void getCourseById_shouldReturnCourseDTOWhenFound() {
        Course course = new Course("Java Basics", "Intro to Java");
        when(courseRepository.findCourseWithLessons(1L)).thenReturn(Optional.of(course));

        CourseDTO result = courseService.getCourseById(1L);

        assertThat(result.title()).isEqualTo("Java Basics");
        assertThat(result.description()).isEqualTo("Intro to Java");
    }

    @Test
    void getCourseById_shouldThrowWhenNotFound() {
        when(courseRepository.findCourseWithLessons(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.getCourseById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Course with id 1 not found");
    }

    // =========================
    // createCourse
    // =========================
    @Test
    void createCourse_shouldSaveCourseAndReturnDTO() {
        CourseLightDTO dto = new CourseLightDTO("Java Basics", "Intro to Java");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CourseDTO result = courseService.createCourse(1L, dto);

        // Verify course saved
        verify(courseRepository).save(courseCaptor.capture());
        Course savedCourse = courseCaptor.getValue();
        assertThat(savedCourse.getTitle()).isEqualTo("Java Basics");
        assertThat(savedCourse.getDescription()).isEqualTo("Intro to Java");

        // Verify bidirectional relationship
        assertThat(savedCourse.getInstructor()).isEqualTo(instructor);
        assertThat(instructor.getCourses()).contains(savedCourse);

        // Verify DTO
        assertThat(result.title()).isEqualTo("Java Basics");
        assertThat(result.description()).isEqualTo("Intro to Java");
    }

    @Test
    void createCourse_shouldThrowWhenInstructorNotFound() {
        CourseLightDTO dto = new CourseLightDTO("Java Basics", "Intro to Java");

        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.createCourse(1L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Instructor with id 1 not found");
    }

    // =========================
    // updateCourse
    // =========================
    @Test
    void updateCourse_shouldUpdateFieldsAndReturnDTO() {
        Course course = new Course("Old Title", "Old Description");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseLightDTO dto = new CourseLightDTO("New Title", "New Description");
        CourseDTO result = courseService.updateCourse(1L, dto);

        assertThat(course.getTitle()).isEqualTo("New Title");
        assertThat(course.getDescription()).isEqualTo("New Description");

        assertThat(result.title()).isEqualTo("New Title");
        assertThat(result.description()).isEqualTo("New Description");
    }

    @Test
    void updateCourse_shouldThrowWhenCourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        CourseLightDTO dto = new CourseLightDTO("New Title", "New Description");

        assertThatThrownBy(() -> courseService.updateCourse(1L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Course with id 1 not found");
    }

    // =========================
    // deleteCourse
    // =========================
    @Test
    void deleteCourse_shouldDeleteWhenExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        courseService.deleteCourse(1L);

        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteCourse_shouldThrowWhenNotFound() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> courseService.deleteCourse(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Course with id 1 not found");
    }

    // =========================
    // getMostPopularCourses
    // =========================
    @Test
    void getMostPopularCourses_shouldReturnListOfDTOs() {
        Course course1 = new Course("Java Basics", "Intro to Java");
        Course course2 = new Course("Spring Boot", "Spring Boot Guide");
        List<Course> courses = List.of(course1, course2);

        when(courseRepository.findMostPopularCourses()).thenReturn(courses);

        List<CourseDTO> result = courseService.getMostPopularCourses();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CourseDTO::title)
                .containsExactly("Java Basics", "Spring Boot");
    }

    @Test
    void getMostPopularCourses_shouldReturnEmptyListWhenNoCourses() {
        when(courseRepository.findMostPopularCourses()).thenReturn(List.of());

        List<CourseDTO> result = courseService.getMostPopularCourses();

        assertThat(result).isEmpty();
    }
}