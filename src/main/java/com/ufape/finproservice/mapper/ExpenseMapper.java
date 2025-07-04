package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.model.Expense;
import com.ufape.finproservice.model.User;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseDTO dto, User user) {
        if (dto == null) return null;
        return Expense.builder()
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

    public static ExpenseResponseDTO toExpenseResponseDTO(Expense expense) {
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
