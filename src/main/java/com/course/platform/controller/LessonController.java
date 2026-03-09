package com.course.platform.controller;

import com.course.platform.dto.LessonDTO;
import com.course.platform.dto.light.LessonLightDTO;
import com.course.platform.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;


    // GET /api/lessons
    @GetMapping
    public List<LessonDTO> getAllLessons() {
        return lessonService.getAllLessons();
    }

    // GET /api/lessons/{id}
    @GetMapping("/{id}")
    public LessonDTO getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    // POST /api/lessons?courseId=1
    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(
            @RequestParam Long courseId,
            @Valid @RequestBody LessonLightDTO dto) {
        LessonDTO created = lessonService.createLesson(courseId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/lessons/{id}
    @PutMapping("/{id}")
    public LessonDTO updateLesson(@PathVariable Long id,
                                  @Valid @RequestBody LessonLightDTO dto) {
        return lessonService.updateLesson(id, dto);
    }

    // DELETE /api/lessons/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
