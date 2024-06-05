package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDto {


    private String accountNo;
    private String name;
    private String phoneNo;
    private String type;
    private String currency;
    private String accountStatus;
    private String kycStatus;
    private String country;
    private String balance;
    private String limit;

}