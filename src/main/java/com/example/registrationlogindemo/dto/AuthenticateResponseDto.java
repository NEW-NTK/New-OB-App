package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class AuthenticateResponseDto {
    private Status status;
    private Data data;
}




