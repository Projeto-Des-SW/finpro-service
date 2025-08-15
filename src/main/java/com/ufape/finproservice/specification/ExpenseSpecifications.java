package com.ufape.finproservice.specification;

import com.ufape.finproservice.model.Expense;
import com.ufape.finproservice.model.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ExpenseSpecifications {

    public static Specification<Expense> belongsToUser(UserEntity user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Expense> hasCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("expenseCategory").get("id"), categoryId);
    }

    public static Specification<Expense> dateAfter(LocalDate startDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<Expense> dateBefore(LocalDate endDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("date"), endDate);
    }
}
