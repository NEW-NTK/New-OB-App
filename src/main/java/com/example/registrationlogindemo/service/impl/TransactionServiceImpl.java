package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.*;
import com.example.registrationlogindemo.entity.BankAccount;
import com.example.registrationlogindemo.entity.Transaction;
import com.example.registrationlogindemo.repository.BankAccountRepository;
import com.example.registrationlogindemo.repository.TransactionRepository;
import com.example.registrationlogindemo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private TransactionRepository transactionRepo;




    @Override
    public FinishTransactionResponseDto UpdateBalance(long sourceaccNo,long destaccNo, float depositAmount) {
        FinishTransactionResponseDto finishTransactionResponseDto = new FinishTransactionResponseDto();
        BankAccount destaccount= bankAccountRepo.findByAccNo(destaccNo);
        BankAccount sourceaccount= bankAccountRepo.findByAccNo(sourceaccNo);


        if(sourceaccount.getBalance() > depositAmount){
            Transaction transaction = new Transaction();
            sourceaccount.setBalance(sourceaccount.getBalance() - depositAmount);
            destaccount.setBalance(destaccount.getBalance() + depositAmount);
            bankAccountRepo.save(destaccount);
            bankAccountRepo.save(sourceaccount);

            LocalDate currentDate = LocalDate.now();
            transaction.setAmount(depositAmount);
            transaction.setTransactionDate(currentDate);
            transaction.setSourceAccount(sourceaccount);
            transaction.setDestinationAccount(destaccount);
           transactionRepo.save(transaction);

            VerifyOtpStatus status = new VerifyOtpStatus();
            status.setCode(0);
            finishTransactionResponseDto.setStatus(status);

            FinishTransactionData data = new FinishTransactionData();
            data.setTransactionId("xxxxxxxxxx");
            data.setTransactionDate(currentDate);
            data.setTransactionHash("xxxxxxxxxx");
            finishTransactionResponseDto.setData(data);
        }
        else{
            VerifyOtpStatus status = new VerifyOtpStatus();
            status.setCode(4);
            status.setErrorCode("9");
            status.setErrorMessage("Transaction Failed");
            finishTransactionResponseDto.setStatus(status);

            FinishTransactionData data = new FinishTransactionData();

            finishTransactionResponseDto.setData(data);
        }
        return finishTransactionResponseDto;
    }







}
