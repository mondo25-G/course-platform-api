package com.course.platform.unit;

import com.course.platform.dto.InstructorDTO;
import com.course.platform.dto.light.InstructorLightDTO;
import com.course.platform.exception.DuplicateResourceException;
import com.course.platform.exception.ResourceNotFoundException;
import com.course.platform.model.Instructor;
import com.course.platform.repository.InstructorRepository;
import com.course.platform.service.InstructorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @InjectMocks
    private InstructorService instructorService;

    @Captor
    private ArgumentCaptor<Instructor> instructorCaptor;

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructor = new Instructor("John Doe", "john@example.com");
        instructor.setId(1L);
    }

    // ===== getAllInstructors =====
    @Test
    void getAllInstructors_shouldReturnListOfDTOs() {
        Instructor instructor2 = new Instructor("Jane Smith", "jane@example.com");
        instructor2.setId(2L);
        when(instructorRepository.findAll()).thenReturn(List.of(instructor, instructor2));

        List<InstructorDTO> result = instructorService.getAllInstructors();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(InstructorDTO::name)
                .containsExactly("John Doe", "Jane Smith");
    }

    // ===== getInstructorById =====
    @Test
    void getInstructorById_shouldReturnDTOWhenFound() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        InstructorDTO result = instructorService.getInstructorById(1L);

        assertThat(result.name()).isEqualTo("John Doe");
        assertThat(result.email()).isEqualTo("john@example.com");
    }

    @Test
    void getInstructorById_shouldThrowWhenNotFound() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> instructorService.getInstructorById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Instructor with id 1 not found");
    }

    // ===== createInstructor =====
    @Test
    void createInstructor_shouldSaveInstructorAndReturnDTO() {
        InstructorLightDTO dto = new InstructorLightDTO("John Doe", "john@example.com");
        when(instructorRepository.existsByEmail(dto.email())).thenReturn(false);
        when(instructorRepository.save(any(Instructor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InstructorDTO result = instructorService.createInstructor(dto);

        verify(instructorRepository).save(instructorCaptor.capture());
        Instructor saved = instructorCaptor.getValue();
        assertThat(saved.getName()).isEqualTo("John Doe");
        assertThat(saved.getEmail()).isEqualTo("john@example.com");

        assertThat(result.name()).isEqualTo("John Doe");
        assertThat(result.email()).isEqualTo("john@example.com");
    }

    @Test
    void createInstructor_shouldThrowDuplicateResourceException() {
        InstructorLightDTO dto = new InstructorLightDTO("John Doe", "john@example.com");
        when(instructorRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThatThrownBy(() -> instructorService.createInstructor(dto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email john@example.comalready exists");
    }

    // ===== updateInstructor =====
    @Test
    void updateInstructor_shouldUpdateInstructorAndReturnDTO() {
        InstructorLightDTO dto = new InstructorLightDTO("John Updated", "john.updated@example.com");
        when(instructorRepository.existsByEmail(dto.email())).thenReturn(false);
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        InstructorDTO result = instructorService.updateInstructor(1L, dto);

        assertThat(instructor.getName()).isEqualTo("John Updated");
        assertThat(instructor.getEmail()).isEqualTo("john.updated@example.com");

        assertThat(result.name()).isEqualTo("John Updated");
        assertThat(result.email()).isEqualTo("john.updated@example.com");
    }

    @Test
    void updateInstructor_shouldThrowDuplicateResourceException() {
        InstructorLightDTO dto = new InstructorLightDTO("John Updated", "john.updated@example.com");
        when(instructorRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThatThrownBy(() -> instructorService.updateInstructor(1L, dto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email john.updated@example.com already exists");
    }

    @Test
    void updateInstructor_shouldThrowResourceNotFoundException() {
        InstructorLightDTO dto = new InstructorLightDTO("John Updated", "john.updated@example.com");
        when(instructorRepository.existsByEmail(dto.email())).thenReturn(false);
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> instructorService.updateInstructor(1L, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Instructor with id 1 not found");
    }

    // ===== deleteInstructor =====
    @Test
    void deleteInstructor_shouldDeleteWhenExists() {
        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorService.deleteInstructor(1L);

        verify(instructorRepository).deleteById(1L);
    }

    @Test
    void deleteInstructor_shouldThrowResourceNotFoundException() {
        when(instructorRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> instructorService.deleteInstructor(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Instructor with id 1 not found");
    }
}