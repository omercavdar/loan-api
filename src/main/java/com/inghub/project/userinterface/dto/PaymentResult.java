package com.inghub.project.userinterface.dto;

import java.math.BigDecimal;

public class PaymentResult {

    private final int installmentsPaid;
    private final BigDecimal totalAmountPaid;
    private final boolean isLoanFullyPaid;

    public PaymentResult(int installmentsPaid, BigDecimal totalAmountPaid, boolean isLoanFullyPaid) {
        this.installmentsPaid = installmentsPaid;
        this.totalAmountPaid = totalAmountPaid;
        this.isLoanFullyPaid = isLoanFullyPaid;
    }

    public int getInstallmentsPaid() {
        return installmentsPaid;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public boolean isLoanFullyPaid() {
        return isLoanFullyPaid;
    }
}
