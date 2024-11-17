package com.inghub.project.application.loan;

import com.inghub.project.domain.customer.Customer;
import com.inghub.project.domain.customer.CustomerRepository;
import com.inghub.project.domain.exception.CustomerNotFoundException;
import com.inghub.project.domain.loan.Loan;
import com.inghub.project.domain.loan.LoanDomainService;
import com.inghub.project.domain.loan.LoanRepository;
import com.inghub.project.userinterface.dto.CreateLoanRequest;
import com.inghub.project.userinterface.dto.LoanDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    private final LoanDomainService loanDomainService;
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;

    public LoanApplicationService(
            LoanDomainService loanDomainService,
            CustomerRepository customerRepository,
            LoanRepository loanRepository) {
        this.loanDomainService = loanDomainService;
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public LoanDto createLoan(CreateLoanRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Loan loan = loanDomainService.createLoan(
                customer,
                request.getAmount(),
                request.getInterestRate(),
                request.getNumberOfInstallments()
        );

        loanRepository.save(loan);

        return new LoanDto(loan);
    }

    public List<LoanDto> listLoans(Long customerId, Optional<Boolean> isPaid) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found");
        }

        List<Loan> loans;
        if (isPaid.isPresent()) {
            loans = loanRepository.findByCustomerIdAndIsPaid(customerId, isPaid.get());
        } else {
            loans = loanRepository.findByCustomerId(customerId);
        }

        return loans.stream()
                .map(LoanDto::new)
                .collect(Collectors.toList());
    }
}
