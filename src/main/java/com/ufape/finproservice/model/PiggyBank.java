package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name= "piggy_bank")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiggyBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long piggyBankId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String status;

    @Column(nullable = false)
    private BigDecimal monthlyDeposit;

    @Column(nullable = false)
    private BigDecimal savingsGoal;

    @Column(nullable = false)
    private LocalDate targetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
