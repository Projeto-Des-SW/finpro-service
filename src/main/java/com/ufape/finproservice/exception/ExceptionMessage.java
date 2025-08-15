package com.ufape.finproservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    EMAIL_ALREADY_EXISTS(400, "Email already exists"),
    USER_NOT_FOUND(404, "User not found"),
    AUTHENTICATION_FAILED(404, "Email or password is incorrect"),
    INCOME_NOT_FOUND(404, "Income not found"),
    INCOME_NOT_OWNED(403, "User doesn't own this income"),
    EXPENSE_NOT_FOUND(404, "Expense not found"),
    EXPENSE_NOT_OWNED(403, "User doesn't own this expense"),

    INCOME_CATEGORY_ALREADY_EXISTS(400, "Income category already exists"),
    INCOME_CATEGORY_NOT_FOUND(404, "Income category not found"),
    CATEGORY_HAS_INCOME(409, "Cannot delete income category that has associated incomes"),
    EXPENSE_CATEGORY_NOT_FOUND(404, "Expense category not found"),
    EXPENSE_CATEGORY_ALREADY_EXISTS(409, "Expense category already exists"),
    PIGGY_BANK_ALREADY_EXISTS(400, "Piggy bank already exists"),
    PIGGY_BANK_NOT_FOUND(404, "Piggy bank not found"),
    PIGGY_BANK_NOT_OWNED(403, "User doesn't own this piggy bank");

    private final int code;
    private final String description;
}
