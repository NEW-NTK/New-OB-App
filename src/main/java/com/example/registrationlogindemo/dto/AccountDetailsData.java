package com.example.registrationlogindemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsData {


      private String accountName;
      private String accountId;
      private String name;
      private String bankName;
      private String email;
      private String phone;
      private boolean frozen;
      private String kycStatus;
      private  String accountStatus;


}
