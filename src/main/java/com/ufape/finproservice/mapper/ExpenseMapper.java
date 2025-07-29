package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.model.ExpenseEntity;
import com.ufape.finproservice.model.UserEntity;

public class ExpenseMapper {

    public static ExpenseEntity toEntity(ExpenseDTO dto, UserEntity user) {
        if (dto == null) return null;
        return ExpenseEntity.builder()
                .expenseId(dto.getId())
                .category(dto.getCategory())
                .amount(dto.getAmount())
                .paymentDestination(dto.getPaymentDestination())
                .balanceSource(dto.getBalanceSource())
                .date(dto.getDate())
                .observation(dto.getObservation())
                .user(user)
                .build();
    }

    public static ExpenseResponseDTO toExpenseResponseDTO(ExpenseEntity expense) {
        if (expense == null) return null;
        return ExpenseResponseDTO.builder()
                .id(expense.getExpenseId())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .paymentDestination(expense.getPaymentDestination())
                .balanceSource(expense.getBalanceSource())
                .date(expense.getDate())
                .observation(expense.getObservation())
                .userId(expense.getUser().getId())
                .build();
    }
}
