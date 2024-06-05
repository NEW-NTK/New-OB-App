package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.FinishTransactionResponseDto;
import com.example.registrationlogindemo.dto.TransactionDetailsDto;
import com.example.registrationlogindemo.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    FinishTransactionResponseDto UpdateBalance(long sourceaccNo,long accNo, float depositAmount);


}
