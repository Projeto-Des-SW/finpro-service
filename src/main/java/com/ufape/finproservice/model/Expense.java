package com.ufape.finproservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "expenseId")
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private String category;
    private BigDecimal amount;
    private String paymentDestination;
    private String balanceSource;
    private LocalDate date;
    private String observation;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;
}
