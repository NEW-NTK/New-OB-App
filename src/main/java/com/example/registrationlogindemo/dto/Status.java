package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    }
