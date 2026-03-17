package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private Integer credits;
    private String lecturer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}