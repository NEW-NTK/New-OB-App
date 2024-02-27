package com.example.registrationlogindemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationStatus {
    private int code;
    private String errorCode;
    private String error;
    private String warning;
    private String message;


}
