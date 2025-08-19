package com.ufape.finproservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalContentDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String videoUrl;

    private String fileUrl;

    @NotBlank
    private String category;
}
