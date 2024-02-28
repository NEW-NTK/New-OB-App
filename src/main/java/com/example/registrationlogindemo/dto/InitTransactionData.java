package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InitTransactionData {
    private String initRefNumber;
    private double debitAmount;
    private String debitCcy;
    private double fee;
    private boolean requireOtp;
}
