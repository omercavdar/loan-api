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
public class PaymentResultDto {
    private int installmentsPaid;
    private BigDecimal totalAmountPaid;
    private boolean isLoanFullyPaid;
}
