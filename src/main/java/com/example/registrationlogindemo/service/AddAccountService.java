package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.AccountDto;
import com.example.registrationlogindemo.dto.AuthenticateResponseDto;
import com.example.registrationlogindemo.dto.VerifyOtpResponseDto;

public interface AddAccountService {

    AuthenticateResponseDto checkUsernamePassword(AccountDto acc);
    VerifyOtpResponseDto checkOtp(String otp);
}
