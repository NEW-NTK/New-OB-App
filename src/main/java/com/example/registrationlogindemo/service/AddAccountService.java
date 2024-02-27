package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.AccountDto;
import com.example.registrationlogindemo.dto.AuthenticateResponseDto;

public interface AddAccountService {

    AuthenticateResponseDto checkUsernamePassword(AccountDto acc);
    AuthenticateResponseDto checkOtp(String otp);
}
