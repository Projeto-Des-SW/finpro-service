package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.EducationalContentDTO;
import com.ufape.finproservice.dto.EducationalContentResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.EducationalContentMapper;
import com.ufape.finproservice.model.EducationalContent;
import com.ufape.finproservice.repository.EducationalContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EducationalContentService {

    private final EducationalContentRepository repository;

    public EducationalContentResponseDTO createContent(EducationalContentDTO dto) {
        EducationalContent entity = EducationalContentMapper.toEntity(dto);
        EducationalContent saved = repository.save(entity);
        return EducationalContentMapper.toResponseDTO(saved);
    }

    public EducationalContentResponseDTO findById(Long id) {
        EducationalContent content = repository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.CONTENT_NOT_FOUND));
        return EducationalContentMapper.toResponseDTO(content);
    }

    public List<EducationalContentResponseDTO> findAll(String category) {
        List<EducationalContent> contents = (category != null && !category.isEmpty())
                ? repository.findByCategory(category)
                : repository.findAll();

        return contents.stream()
                .map(EducationalContentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EducationalContentResponseDTO updateContent(Long id, EducationalContentDTO dto) {
        EducationalContent existing = repository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.CONTENT_NOT_FOUND));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setVideoUrl(dto.getVideoUrl());
        existing.setFileUrl(dto.getFileUrl());
        existing.setCategory(dto.getCategory());

        EducationalContent updated = repository.save(existing);
        return EducationalContentMapper.toResponseDTO(updated);
    }

    public void deleteContent(Long id) {
        EducationalContent content = repository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.CONTENT_NOT_FOUND));

        repository.delete(content);
    }
}
