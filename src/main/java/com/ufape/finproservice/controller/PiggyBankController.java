package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.PiggyBankDTO;
import com.ufape.finproservice.dto.PiggyBankResponseDTO;
import com.ufape.finproservice.service.PiggyBankService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/piggy-bank")
@AllArgsConstructor
public class PiggyBankController {

    private final PiggyBankService piggyBankService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PiggyBankResponseDTO createPiggyBank(@RequestBody @Valid PiggyBankDTO piggyBankDTO) {
        return piggyBankService.createPiggyBank(piggyBankDTO);
    }

    @GetMapping("/{id}")
    public PiggyBankResponseDTO getPiggyBankById(@PathVariable Long id) {
        return piggyBankService.findPiggyBankById(id);
    }

    @GetMapping
    public List<PiggyBankResponseDTO> getAllUserPiggyBanks() {
        return piggyBankService.findAllUserPiggyBanks();
    }

    @GetMapping("/search")
    public List<PiggyBankResponseDTO> getPiggyBanksByName(@RequestParam String name) {
        return piggyBankService.findPiggyBanksByName(name);
    }

    @PutMapping("/{id}")
    public PiggyBankResponseDTO updatePiggyBank(
            @PathVariable Long id,
            @RequestBody @Valid PiggyBankDTO piggyBankDTO) {
        return piggyBankService.updatePiggyBank(id, piggyBankDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePiggyBank(@PathVariable Long id) {
        piggyBankService.deletePiggyBank(id);
    }
}