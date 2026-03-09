package com.course.platform.controller;

import com.course.platform.dto.CourseDTO;
import com.course.platform.dto.light.CourseLightDTO;
import com.course.platform.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(
            @RequestParam Long instructorId,
            @Valid @RequestBody CourseLightDTO dto) {
        CourseDTO created = courseService.createCourse(instructorId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable Long id,
                                  @Valid @RequestBody CourseLightDTO dto) {
        return courseService.updateCourse(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/popular")
    public List<CourseDTO> getMostPopularCourses() {
        return courseService.getMostPopularCourses();
    }
}
