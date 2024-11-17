package com.inghub.project.userinterface.dto;

import com.inghub.project.domain.model.Loan;
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
public class LoanDto {

    private Long id;
    private Long customerId;
    private BigDecimal loanAmount;
    private int numberOfInstallments;
    private BigDecimal interestRate;
    private LocalDate createDate;
    private boolean isPaid;

    public LoanDto(Loan loan) {
        this.id = loan.getId();
        this.customerId = loan.getCustomer() != null ? loan.getCustomer().getId() : null;
    }
}
