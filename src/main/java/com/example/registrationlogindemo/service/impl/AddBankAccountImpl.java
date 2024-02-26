package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.AccountDetailsDto;
import com.example.registrationlogindemo.service.AddBankAccountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddBankAccountImpl implements AddBankAccountService {


    private Map<String, List<AccountDetailsDto>> bankAccountsMap = new HashMap<>();

//    void addBankAccount(AccountDetailsDto accountDetails, String bankname);

    @Override
    public void addBankAccount(AccountDetailsDto accountDetails, String bankname) {

        if (!bankAccountsMap.containsKey(bankname)) {
            bankAccountsMap.put(bankname, new ArrayList<>());
        }

        List<AccountDetailsDto> accounts = bankAccountsMap.get(bankname);
        accounts.add(accountDetails);
    }
}
