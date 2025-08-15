package com.ufape.finproservice.controller;

import com.ufape.finproservice.dto.PiggyBankDTO;
import com.ufape.finproservice.dto.PiggyBankResponseDTO;
import com.ufape.finproservice.service.PiggyBankService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
