package com.inghub.project.userinterface.dto;

import com.inghub.project.domain.loaninstallment.LoanInstallment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanInstallmentDto {

    private Long id;
    private BigDecimal amount;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean isPaid;

    public LoanInstallmentDto(LoanInstallment installment) {
        this.id = installment.getId();
        this.amount = installment.getAmount();
        this.paidAmount = installment.getPaidAmount();
        this.dueDate = installment.getDueDate();
        this.paymentDate = installment.getPaymentDate();
        this.isPaid = installment.getIsPaid();
    }
}
