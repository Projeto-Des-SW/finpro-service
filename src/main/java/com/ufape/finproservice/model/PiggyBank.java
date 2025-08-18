package com.ufape.finproservice.model;

import com.ufape.finproservice.enumeration.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private BigDecimal monthlyDeposit;

    @Column(nullable = false)
    private BigDecimal savingsGoal;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private Integer depositDay;

    @Column
    private LocalDate lastDepositDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
