package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.EducationalContentDTO;
import com.ufape.finproservice.dto.EducationalContentResponseDTO;
import com.ufape.finproservice.service.EducationalContentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/educational-content")
@AllArgsConstructor
public class EducationalContentController {

    private final EducationalContentService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public EducationalContentResponseDTO create(@RequestBody @Valid EducationalContentDTO dto) {
        return service.createContent(dto);
    }

    @GetMapping("/{id}")
    public EducationalContentResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<EducationalContentResponseDTO> getAll(@RequestParam(required = false) String category) {
        return service.findAll(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EducationalContentResponseDTO update(@PathVariable Long id, @RequestBody @Valid EducationalContentDTO dto) {
        return service.updateContent(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteContent(id);
    }
}
