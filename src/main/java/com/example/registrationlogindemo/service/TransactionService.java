package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.FinishTransactionResponseDto;

public interface TransactionService {
    FinishTransactionResponseDto UpdateBalance(long sourceaccNo,long accNo, float depositAmount);

}
