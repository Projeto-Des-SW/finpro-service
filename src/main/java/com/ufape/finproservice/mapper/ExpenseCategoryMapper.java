package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.ExpenseCategoryDTO;
import com.ufape.finproservice.dto.ExpenseCategoryResponseDTO;
import com.ufape.finproservice.model.ExpenseCategory;

public class ExpenseCategoryMapper {

    public static ExpenseCategory toEntity(ExpenseCategoryDTO dto) {
        if (dto == null) return null;

        return ExpenseCategory.builder()
                .name(dto.getName())
                .build();
    }

    public static ExpenseCategoryResponseDTO toExpenseCategoryResponseDTO(ExpenseCategory expenseCategory) {
        if (expenseCategory == null) return null;

        return ExpenseCategoryResponseDTO.builder()
                .expenseCategoryId(expenseCategory.getId())
                .name(expenseCategory.getName())
                .build();
    }

    public static ExpenseCategoryDTO toExpenseCategoryDTO(ExpenseCategory expenseCategory) {
        if (expenseCategory == null) return null;

        return ExpenseCategoryDTO.builder()
                .name(expenseCategory.getName())
                .build();
    }
}
