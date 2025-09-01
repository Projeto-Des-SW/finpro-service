package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.IncomeDTO;
import com.ufape.finproservice.dto.response.IncomeResponseDTO;
import com.ufape.finproservice.service.IncomeService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/income")
@AllArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncomeResponseDTO createIncome(@RequestBody @Valid IncomeDTO incomeDTO) {
        return incomeService.createIncome(incomeDTO);
    }

    @GetMapping("/{id}")
    public IncomeResponseDTO getIncomeById(@PathVariable Long id) {
        return incomeService.findIncomeById(id);
    }

    @GetMapping
    public List<IncomeResponseDTO> getAllUserIncomes() {
        return incomeService.findAllUserIncomes();
    }

    @PutMapping("/{id}")
    public IncomeResponseDTO updateIncome(
            @PathVariable Long id,
            @RequestBody @Valid IncomeDTO incomeDTO) {
        return incomeService.updateIncome(id, incomeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
    }

    @GetMapping("/period")
    public List<IncomeResponseDTO> getIncomesByPeriod(
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String startDate,
            @RequestParam @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String endDate) {
        return incomeService.findIncomesByPeriod(startDate, endDate);
    }

    @GetMapping("/category/{categoryId}")
    public List<IncomeResponseDTO> getIncomesByIncomeCategory(@PathVariable Long categoryId) {
        return incomeService.findIncomesByCategory(categoryId);
    }

    @GetMapping("/user/category/{categoryId}")
    public List<IncomeResponseDTO> getAllIncomesByCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        return incomeService.findAllIncomes(categoryId, startDate, endDate);
    }
}
