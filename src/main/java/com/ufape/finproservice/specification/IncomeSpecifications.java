package com.ufape.finproservice.specification;

import com.ufape.finproservice.model.Income;
import com.ufape.finproservice.model.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public final class IncomeSpecifications {

    private IncomeSpecifications() {}

    public static Specification<Income> belongsToUser(UserEntity user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Income> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(root.get("incomeCategory").get("id"), categoryId);
    }

    public static Specification<Income> dateOnOrAfter(LocalDate startDate) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<Income> dateOnOrBefore(LocalDate endDate) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("date"), endDate);
    }
}
