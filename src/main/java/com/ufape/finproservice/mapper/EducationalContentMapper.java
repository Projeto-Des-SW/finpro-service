package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.EducationalContentDTO;
import com.ufape.finproservice.dto.response.EducationalContentResponseDTO;
import com.ufape.finproservice.model.EducationalContent;

public class EducationalContentMapper {

    public static EducationalContent toEntity(EducationalContentDTO dto) {
        if (dto == null) return null;

        return EducationalContent.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .videoUrl(dto.getVideoUrl())
                .fileUrl(dto.getFileUrl())
                .category(dto.getCategory())
                .build();
    }

    public static EducationalContentResponseDTO toResponseDTO(EducationalContent entity) {
        if (entity == null) return null;

        return EducationalContentResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .videoUrl(entity.getVideoUrl())
                .fileUrl(entity.getFileUrl())
                .category(entity.getCategory())
                .build();
    }
}
