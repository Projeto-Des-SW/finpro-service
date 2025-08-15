package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.PiggyBank;

import java.util.List;
import java.util.Optional;

public interface PiggyBankRespository {
    List<PiggyBank> findByUserId(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<PiggyBank> findByPiggyBankIdAndUserID(Long piggyBankId, Long userId);

    List<PiggyBank> findByNameContainingIgnoreCaseAndUserId(String name, Long userId);

}
