package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.IncomeCategoryDTO;
import com.ufape.finproservice.dto.IncomeCategoryResponseDTO;
import com.ufape.finproservice.model.IncomeCategory;

public class IncomeCategoryMapper {

    public static IncomeCategory toEntity(IncomeCategoryDTO dto) {
        if (dto == null) return null;

        return IncomeCategory.builder()
                .name(dto.getName())
                .build();
    }

    public static IncomeCategoryResponseDTO toIncomeCategoryResponseDTO(IncomeCategory incomeCategory){
        if (incomeCategory == null) return null;

        return IncomeCategoryResponseDTO.builder()
                .incomeCategoryId(incomeCategory.getId())
                .name(incomeCategory.getName())
                .build();
    }

    public static IncomeCategoryDTO toIncomeCategoryDTO(IncomeCategory incomeCategory){
        if (incomeCategory == null) return null;

        return IncomeCategoryDTO.builder()
                .name(incomeCategory.getName())
                .build();
    }
}
