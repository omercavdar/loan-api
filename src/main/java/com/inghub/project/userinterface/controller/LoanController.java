package com.inghub.project.userinterface.controller;

import com.inghub.project.application.loan.LoanApplicationService;
import com.inghub.project.domain.exception.InvalidLoanParametersException;
import com.inghub.project.userinterface.dto.CreateLoanRequest;
import com.inghub.project.userinterface.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    public LoanController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public ResponseEntity<String> createLoan(@RequestBody CreateLoanRequest request) {
        try {
            LoanDto loanDto = loanApplicationService.createLoan(request);
            return ResponseEntity.ok("Successfully created loan with id: " + loanDto.getId());
        } catch (InvalidLoanParametersException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<LoanDto>> listLoans(
            @RequestParam Long customerId,
            @RequestParam Optional<Boolean> isPaid
    ) {
        List<LoanDto> loans = loanApplicationService.listLoans(customerId, isPaid);
        return ResponseEntity.ok(loans);
    }
}
