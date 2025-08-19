package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.EducationalContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationalContentRepository extends JpaRepository<EducationalContent, Long> {
    List<EducationalContent> findByCategory(String category);
}
