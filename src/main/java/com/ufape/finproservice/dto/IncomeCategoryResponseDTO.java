package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IncomeCategoryResponseDTO {
    private Long incomeCategoryId;
    private String name;
}
