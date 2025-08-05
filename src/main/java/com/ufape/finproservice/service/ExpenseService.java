package com.ufape.finproservice.service;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.exception.CustomException;
import com.ufape.finproservice.exception.ExceptionMessage;
import com.ufape.finproservice.mapper.ExpenseMapper;
import com.ufape.finproservice.model.Expense;
import com.ufape.finproservice.model.ExpenseCategory;
import com.ufape.finproservice.model.UserEntity;
import com.ufape.finproservice.repository.ExpenseCategoryRepository;
import com.ufape.finproservice.repository.ExpenseRepository;
import com.ufape.finproservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseResponseDTO createExpense(ExpenseDTO expenseDTO) {
        UserEntity user = getCurrentUser();

        ExpenseCategory category = expenseCategoryRepository.findById(expenseDTO.getExpenseCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));
        
        Expense expense = ExpenseMapper.toEntity(expenseDTO, user, category);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.toExpenseResponseDTO(savedExpense);
    }

    public ExpenseResponseDTO findExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_NOT_FOUND));
        
        validateUserOwnership(expense);
        return ExpenseMapper.toExpenseResponseDTO(expense);
    }

    public List<ExpenseResponseDTO> findAllUserExpenses() {
        UserEntity user = getCurrentUser();
        return expenseRepository.findByUserId(user.getId()).stream()
                .map(ExpenseMapper::toExpenseResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> findExpensesByPeriod(String startDateStr, String endDateStr) {
        UserEntity user = getCurrentUser();
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
        
        return expenseRepository.findByUserIdAndDateBetween(user.getId(), startDate, endDate).stream()
                .map(ExpenseMapper::toExpenseResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_NOT_FOUND));
        
        validateUserOwnership(existingExpense);

        ExpenseCategory category = expenseCategoryRepository.findById(expenseDTO.getExpenseCategoryId())
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));
        
        existingExpense.setDate(expenseDTO.getDate());
        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setPaymentDestination(expenseDTO.getPaymentDestination());
        existingExpense.setBalanceSource(expenseDTO.getBalanceSource());
        existingExpense.setObservation(expenseDTO.getObservation());
        existingExpense.setExpenseCategory(category);
        
        Expense updatedExpense = expenseRepository.save(existingExpense);
        return ExpenseMapper.toExpenseResponseDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_NOT_FOUND));
        
        validateUserOwnership(expense);
        expenseRepository.delete(expense);
    }

    public List<ExpenseResponseDTO> findExpensesByCategory(Long categoryId) {
        UserEntity user = getCurrentUser();

        expenseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));

        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseCategoryId(user.getId(), categoryId);
        return expenses.stream()
                .map(ExpenseMapper::toExpenseResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDTO> findAllExpensesByCategory(Long categoryId) {
        expenseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionMessage.EXPENSE_CATEGORY_NOT_FOUND));

        List<Expense> expenses = expenseRepository.findByExpenseCategoryId(categoryId);
        return expenses.stream()
                .map(ExpenseMapper::toExpenseResponseDTO)
                .collect(Collectors.toList());
    }

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
               .orElseThrow(() -> new CustomException(ExceptionMessage.USER_NOT_FOUND));
    }

    private void validateUserOwnership(Expense expense) {
        UserEntity currentUser = getCurrentUser();
        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(ExceptionMessage.EXPENSE_NOT_OWNED);
        }
    }
}
