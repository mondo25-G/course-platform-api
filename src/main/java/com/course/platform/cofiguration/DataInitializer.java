package com.course.platform.cofiguration;


import com.course.platform.model.*;
import com.course.platform.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            InstructorRepository instructorRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository,
            LessonRepository lessonRepository,
            EnrollmentRepository enrollmentRepository) {

        return args -> {

            // ----- Instructors -----
            Instructor instructor1 = new Instructor("John Doe", "john.doe@email.com");
            Instructor instructor2 = new Instructor("Jane Smith", "jane.smith@email.com");

            instructorRepository.save(instructor1);
            instructorRepository.save(instructor2);

            // ----- Courses -----
            Course course1 = new Course("Spring Boot Masterclass", "Learn Spring Boot from scratch");
            Course course2 = new Course("Java Fundamentals", "Core Java programming");

            course1.setInstructor(instructor1);
            course2.setInstructor(instructor2);

            courseRepository.save(course1);
            courseRepository.save(course2);

            // ----- Lessons -----
            Lesson lesson1 = new Lesson("Introduction", "https://video.com/intro", 1);
            Lesson lesson2 = new Lesson("Spring Boot Setup", "https://video.com/setup", 2);

            lesson1.setCourse(course1);
            lesson2.setCourse(course1);

            lessonRepository.save(lesson1);
            lessonRepository.save(lesson2);

            // ----- Students -----
            Student student1 = new Student("Alice Brown", "alice@email.com");
            Student student2 = new Student("Bob White", "bob@email.com");

            studentRepository.save(student1);
            studentRepository.save(student2);

            // ----- Enrollments -----
            Enrollment enrollment1 = new Enrollment();
            enrollment1.setStudent(student1);
            enrollment1.setCourse(course1);

            Enrollment enrollment2 = new Enrollment();
            enrollment2.setStudent(student2);
            enrollment2.setCourse(course2);

            enrollmentRepository.save(enrollment1);
            enrollmentRepository.save(enrollment2);

        };
    }
}

