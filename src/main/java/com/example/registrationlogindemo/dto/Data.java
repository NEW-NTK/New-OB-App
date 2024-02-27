package com.example.registrationlogindemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private String id_token;
    private boolean isValid;
}
