package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.*;

public interface AddAccountService {

    AuthenticateResponseDto checkUsernamePassword(AccountDto acc);
    VerifyOtpResponseDto checkOtp(String otp);

    CheckAccountNoResponseDto checkAccountNo(long No);

    AccountDetailsResponseDto getAccountDetails(long AccNo);

    AccountDetailsDto getAccountOverview(long AccNo);
}
