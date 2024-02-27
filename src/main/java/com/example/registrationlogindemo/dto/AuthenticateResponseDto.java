package com.example.registrationlogindemo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticateResponseDto {
    private AuthenticationStatus status;
    private AuthenticateData data;


}




