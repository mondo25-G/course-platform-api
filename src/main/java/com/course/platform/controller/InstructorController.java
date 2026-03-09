package com.course.platform.controller;

import com.course.platform.dto.InstructorDTO;
import com.course.platform.dto.light.InstructorLightDTO;
import com.course.platform.model.Instructor;
import com.course.platform.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    // GET /api/instructors
    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        List<InstructorDTO> instructors =instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    // GET /api/instructors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        InstructorDTO instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructor);
    }

    // POST /api/instructors
    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorLightDTO dto) {
        InstructorDTO created = instructorService.createInstructor(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/instructors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable Long id,
                                          @Valid @RequestBody InstructorLightDTO dto) {
        InstructorDTO updated = instructorService.updateInstructor(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/instructors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
