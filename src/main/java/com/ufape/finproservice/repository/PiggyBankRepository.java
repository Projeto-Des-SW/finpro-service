package com.ufape.finproservice.repository;

import com.ufape.finproservice.model.PiggyBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PiggyBankRepository extends JpaRepository<PiggyBank, Long> {
    List<PiggyBank> findByUserId(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<PiggyBank> findByPiggyBankIdAndUserId(Long piggyBankId, Long userId);

    List<PiggyBank> findByNameContainingIgnoreCaseAndUserId(String name, Long userId);

}
