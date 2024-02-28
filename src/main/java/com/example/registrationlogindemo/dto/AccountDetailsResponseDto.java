package com.example.registrationlogindemo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDetailsResponseDto {
    private AuthenticationStatus status;
    private AccountDetailsData data;
}
