package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "educational_content")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column
    private String videoUrl;

    @Column
    private String fileUrl;

    @Column(nullable = false)
    private String category;

}