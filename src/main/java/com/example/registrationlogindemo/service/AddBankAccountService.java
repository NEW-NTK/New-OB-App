package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.AccountDetailsDto;

public interface AddBankAccountService {

    void addBankAccount(AccountDetailsDto accountDetails, String bankname);
}
