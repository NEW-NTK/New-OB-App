package com.example.registrationlogindemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckAccountNoResponseDto {
    private CheckAccountNoStatus status;
    private CheckAccountNoData data;
}
