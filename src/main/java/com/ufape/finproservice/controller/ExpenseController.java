package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.ExpenseDTO;
import com.ufape.finproservice.dto.ExpenseResponseDTO;
import com.ufape.finproservice.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*import java.math.BigDecimal;
import java.time.LocalDate;*/
import java.util.List;

@RestController
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

    @PostMapping("/create")
    @Operation
    public ResponseEntity<ExpenseResponseDTO> registerExpense(@RequestBody ExpenseDTO dto) {
        ExpenseResponseDTO saved = expenseService.registerExpense(dto);
        return ResponseEntity.ok(saved);
    }
/*
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDTO>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.listByUser(userId));
    }

    @GetMapping("/period")
    public ResponseEntity<List<ExpenseResponseDTO>> listByPeriod(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(expenseService.listByPeriod(userId, start, end));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> calculateTotalByCategory(
            @RequestParam Long userId,
            @RequestParam String category) {
        return ResponseEntity.ok(expenseService.calculateTotalByCategory(userId, category));
    }*/
}
