package com.course.platform.controller;

import com.course.platform.dto.InstructorDTO;
import com.course.platform.dto.light.InstructorLightDTO;
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
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    // GET /api/instructors/{id}
    @GetMapping("/{id}")
    public InstructorDTO getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }

    // POST /api/instructors
    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorLightDTO dto) {
        InstructorDTO created = instructorService.createInstructor(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/instructors/{id}
    @PutMapping("/{id}")
    public InstructorDTO updateInstructor(@PathVariable Long id,
                                          @Valid @RequestBody InstructorLightDTO dto) {
        return instructorService.updateInstructor(id, dto);
    }

    // DELETE /api/instructors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
}
