package com.course.platform.service;

import com.course.platform.dto.StudentDTO;
import com.course.platform.dto.light.StudentLightDTO;
import com.course.platform.exception.DuplicateResourceException;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.mapper.Mapper;
import com.course.platform.model.Student;
import com.course.platform.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    // Get all students
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(Mapper::toStudentDTO)
                .collect(Collectors.toList());
    }

    // Get a student by ID
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findStudentWithEnrollments(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
        return Mapper.toStudentDTO(student);
    }

    // Create a student
    public StudentDTO createStudent(StudentLightDTO dto) {
        if (studentRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email "+dto.email()+ " already exists");
        }

        Student student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());

        Student saved = studentRepository.save(student);
        return Mapper.toStudentDTO(saved);
    }

    // Update a student
    public StudentDTO updateStudent(Long id, StudentLightDTO dto) {

        if (studentRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email "+dto.email()+ " already exists");
        }

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student with id " + id + " not found"));

        student.setName(dto.name());
        student.setEmail(dto.email());

        return Mapper.toStudentDTO(student);
    }

    // Delete a student
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
    }
}
