# Course Management Platform – Spring Boot Backend Project

## Project Overview

The **Course Management Platform** is a backend application built with **Spring Boot**, **JPA/Hibernate**, and **PostgreSQL**, packaged using **Docker**. It simulates a **Learning Management System (LMS)** where:

- Instructors can create courses and lessons.
- Students can enroll in courses and access content.

## Key Features

### Course & Lesson Management
- CRUD operations for courses and lessons
- Lessons are linked to courses, maintaining bidirectional relationships
- Supports ordering of lessons within a course

### Student Enrollment
- Students can enroll in courses
- Enrollments automatically track the timestamp of enrollment

### Database Seeding
- Runs automatically when the application starts in the **test** profile
- Populates the database with sample instructors, students, courses, lessons, and enrollments
- Database remains empty when running in other profiles

### Validation & Error Handling
- Input validation using `@Valid` and field annotations (`@NotBlank`, `@NotNull`)
- Centralized exception handling using `@RestControllerAdvice` with custom exceptions

### REST API Design
- Layered architecture: Controller → Service → Repository → Entity
- DTOs used to separate API responses from internal entities
- Proper HTTP status codes returned for all operations

### API Documentation
- Swagger/OpenAPI integration for interactive API exploration
- Easy testing of endpoints

### Dockerized Deployment
- Application and PostgreSQL database can be run via Docker
- Simplifies local development and testing