package com.course.platform.service;

import com.course.platform.dto.EnrollmentDTO;
import com.course.platform.dto.light.EnrollmentLightDTO;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.mapper.Mapper;
import com.course.platform.model.Course;
import com.course.platform.model.Enrollment;
import com.course.platform.model.Student;
import com.course.platform.repository.CourseRepository;
import com.course.platform.repository.EnrollmentRepository;
import com.course.platform.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    // Get all enrollments
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(Mapper::toEnrollmentDTO)
                .collect(Collectors.toList());
    }

    // Get enrollment by ID
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment with id " + id + " not found"));
        return Mapper.toEnrollmentDTO(enrollment);
    }

    // Create a new enrollment
    public EnrollmentDTO createEnrollment(EnrollmentLightDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student with id " + dto.studentId() + " not found"));
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + dto.courseId() + " not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return Mapper.toEnrollmentDTO(saved);
    }

    // Delete an enrollment
    public void deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enrollment with id " + id + " not found");
        }
        enrollmentRepository.deleteById(id);
    }
}
