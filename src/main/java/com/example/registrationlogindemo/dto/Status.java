package com.example.registrationlogindemo.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonFilter("customFilter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private int code;
    private String errorCode;
    private String error;
    private String warning;
    private String message;
    private String errorMessage;
}
