package com.course.platform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String videoUrl;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Lesson() {}

    public Lesson(String title, String videoUrl, int position) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.position = position;
    }

    // getters & setters
}
