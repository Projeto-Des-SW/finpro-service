package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.IncomeCategoryDTO;
import com.ufape.finproservice.dto.response.IncomeCategoryResponseDTO;
import com.ufape.finproservice.service.IncomeCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income-category")
@AllArgsConstructor
public class IncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncomeCategoryResponseDTO createIncomeCategory(@RequestBody @Valid IncomeCategoryDTO incomeCategoryDTO) {
        return incomeCategoryService.createIncomeCategory(incomeCategoryDTO);
    }

    @GetMapping("/{id}")
    public IncomeCategoryResponseDTO  getIncomeCategory(@PathVariable Long id) {
        return incomeCategoryService.findIncomeCategoryById(id);
    }

    @GetMapping
    public List<IncomeCategoryResponseDTO> getAllIncomeCategory() {
        return incomeCategoryService.findAllIncomeCategories();
    }

    @PutMapping("/{id}")
    public IncomeCategoryResponseDTO updateIncomeCategory(
            @PathVariable Long id,
            @RequestBody @Valid IncomeCategoryDTO incomeCategoryDTO) {
        return incomeCategoryService.updateIncomeCategory(id, incomeCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncomeCategory(@PathVariable Long id) {
        incomeCategoryService.deleteIncomeCategory(id);
    }

}
