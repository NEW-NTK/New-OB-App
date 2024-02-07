package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.AccountDto;
import org.springframework.stereotype.Service;
import com.example.registrationlogindemo.service.AddAccountService;
@Service
public class AddAccountServiceImpl implements AddAccountService {

    @Override
    public void addAccount(AccountDto acc){
        System.out.println("account added");
    }
}
