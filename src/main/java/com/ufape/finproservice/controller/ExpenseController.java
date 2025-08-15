package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.service.ExpenseService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponseDTO createExpense(@RequestBody @Valid ExpenseDTO expenseDTO) {
        return expenseService.createExpense(expenseDTO);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO getExpenseById(@PathVariable Long id) {
        return expenseService.findExpenseById(id);
    }

    @GetMapping
    public List<ExpenseResponseDTO> getAllUserExpenses() {
        return expenseService.findAllUserExpenses();
    }

    @PutMapping("/{id}")
    public ExpenseResponseDTO updateExpense(
            @PathVariable Long id,
            @RequestBody @Valid ExpenseDTO expenseDTO) {
        return expenseService.updateExpense(id, expenseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/period")
    public List<ExpenseResponseDTO> getExpensesByPeriod(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String startDate,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String endDate) {
        return expenseService.findExpensesByPeriod(startDate, endDate);
    }

    @GetMapping("/category/{categoryId}")
    public List<ExpenseResponseDTO> getExpensesByExpenseCategory(@PathVariable Long categoryId) {
        return expenseService.findExpensesByCategory(categoryId);
    }

    @GetMapping("/user/category/{categoryId}")
    public List<ExpenseResponseDTO> getAllExpensesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        return expenseService.findAllExpenses(categoryId, startDate, endDate);
    }
}
