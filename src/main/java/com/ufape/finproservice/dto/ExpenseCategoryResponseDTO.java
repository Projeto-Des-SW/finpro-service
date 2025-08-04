package com.ufape.finproservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExpenseCategoryResponseDTO {
    private Long expenseCategoryId;
    private String name;
}
