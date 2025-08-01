package com.ufape.finproservice.mapper;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.model.Expense;
import com.ufape.finproservice.model.UserEntity;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseDTO dto, UserEntity user) { //ExpenseCategoryEntity category
        if (dto == null) return null;

        return Expense.builder()
                .date(dto.getDate())
                .amount(dto.getAmount())
                .paymentDestination(dto.getPaymentDestination())
                .balanceSource(dto.getBalanceSource())
                .observation(dto.getObservation())
                .user(user)
                //.category(category)
                .build();
    }

    public static ExpenseResponseDTO toExpenseResponseDTO(Expense expense) {
        if (expense == null) return null;

        return ExpenseResponseDTO.builder()
                .expenseid(expense.getExpenseId())
                .date(expense.getDate())
                .amount(expense.getAmount())
                .paymentDestination(expense.getPaymentDestination())
                .balanceSource(expense.getBalanceSource())
                .observation(expense.getObservation())
                .userId(expense.getUser().getId())
                /*.category(expense.getCategory()) != null ?
                    ExpenseCategoryMapper.toExpenseCategoryResponseDTO(expense.getCategory()) : null)*/
                .build();
    }
}
