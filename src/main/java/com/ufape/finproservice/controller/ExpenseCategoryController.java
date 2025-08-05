package com.ufape.finproservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ufape.finproservice.dto.ExpenseCategoryDTO;
import com.ufape.finproservice.dto.ExpenseCategoryResponseDTO;
import com.ufape.finproservice.service.ExpenseCategoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/expense-category")
@AllArgsConstructor
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseCategoryResponseDTO createExpenseCategory(@RequestBody @Valid ExpenseCategoryDTO expenseCategoryDTO) {
        return expenseCategoryService.createExpenseCategory(expenseCategoryDTO);
    }

    @GetMapping("/{id}")
    public ExpenseCategoryResponseDTO getExpenseCategory(@PathVariable Long id) {
        return expenseCategoryService.findExpenseCategoryById(id);
    }

    @GetMapping
    public List<ExpenseCategoryResponseDTO> getAllExpenseCategories() {
        return expenseCategoryService.findAllExpenseCategories();
    }

    @PutMapping("/{id}")
    public ExpenseCategoryResponseDTO updateExpenseCategory(
            @PathVariable Long id,
            @RequestBody @Valid ExpenseCategoryDTO expenseCategoryDTO) {
        return expenseCategoryService.updateExpenseCategory(id, expenseCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpenseCategory(@PathVariable Long id) {
        expenseCategoryService.deleteExpenseCategory(id);
    }
}
