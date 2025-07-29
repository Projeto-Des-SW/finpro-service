package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "incomes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID incomeId;

    @Column(nullable = false)
    private LocalDate date;

    /*@ManyToOne
    @JoinColumn(name = "income_category_id")
    private IncomeCategoryEntity category;*/

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentOrigin;

    @Column(nullable = false)
    private String balanceSource;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}