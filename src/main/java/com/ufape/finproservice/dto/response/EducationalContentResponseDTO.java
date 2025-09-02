package com.ufape.finproservice.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalContentResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String fileUrl;
    private String category;
}
