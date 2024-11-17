package com.inghub.project.userinterface.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLoanRequest {
    private Long customerId;
    private BigDecimal amount;
    private BigDecimal interestRate;
    private int numberOfInstallments;
}
