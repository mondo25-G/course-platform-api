package com.course.platform.service;

import com.course.platform.dto.InstructorDTO;
import com.course.platform.dto.light.InstructorLightDTO;
import com.course.platform.exception.DuplicateResourceException;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.mapper.Mapper;
import com.course.platform.model.Instructor;
import com.course.platform.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    // Get all instructors
    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(Mapper::toInstructorDTO)
                .collect(Collectors.toList());
    }

    // Get instructor by ID
    public InstructorDTO getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instructor with id " + id + " not found"));
        return Mapper.toInstructorDTO(instructor);
    }

    // Create a new instructor
    public InstructorDTO createInstructor(InstructorLightDTO dto) {

        if (instructorRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email "+dto.email()+ "already exists");
        }

        Instructor instructor = new Instructor();
        instructor.setName(dto.name());
        instructor.setEmail(dto.email());

        Instructor saved = instructorRepository.save(instructor);
        return Mapper.toInstructorDTO(saved);
    }

    // Update an existing instructor
    public InstructorDTO updateInstructor(Long id, InstructorLightDTO dto) {

        if (instructorRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email "+dto.email()+ " already exists");
        }

        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instructor with id " + id + " not found"));

        instructor.setName(dto.name());
        instructor.setEmail(dto.email());

        return Mapper.toInstructorDTO(instructor);
    }

    // Delete an instructor
    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instructor with id " + id + " not found");
        }
        instructorRepository.deleteById(id);
    }
}