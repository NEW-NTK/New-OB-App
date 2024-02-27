package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.AccountDto;
import com.example.registrationlogindemo.dto.AuthenticateResponseDto;
import com.example.registrationlogindemo.dto.Data;
import com.example.registrationlogindemo.dto.Status;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.registrationlogindemo.service.AddAccountService;

import java.awt.dnd.DropTarget;

@Service
public class AddAccountServiceImpl implements AddAccountService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public AuthenticateResponseDto checkUsernamePassword(AccountDto acc) {
        User user = userRepo.findByPhoneNoAndPassword(acc.getUsername(), acc.getPassword());
        AuthenticateResponseDto authenticateduser = new AuthenticateResponseDto();

        if (user == null) {
            Status status = new Status();
            status.setCode(4);
            status.setErrorCode("11");
            status.setError("The username or password is incorrect");
            status.setWarning("");
            status.setMessage("");
            authenticateduser.setStatus(status);

            Data data = new Data();
            data.setId_token("");
            authenticateduser.setData(data);


        } else {
            Status status = new Status();
            status.setCode(0);
            status.setErrorCode("null");
            status.setError("null");
            status.setWarning("null");
            status.setMessage("null");
            authenticateduser.setStatus(status);

            Data data = new Data();
            data.setId_token("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlVTRVIiLCJBRE1JTiJdLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzczMTk3MiwiZXhwIjoxNzA3NzMyMjcyfQ.vspr2zYxzWhNVyfcYwrf83udasn8kT01t47561elUVIGykM8zG77vsnFvjONmUNhu3Oi29WIMWSgC6xehtKEyg");

            authenticateduser.setData(data);


        }
        return authenticateduser;
    }

    @Override
    public AuthenticateResponseDto checkOtp(String otp) {
        Boolean checkOtp = true;
        AuthenticateResponseDto authenticateduser = new AuthenticateResponseDto();

        if (checkOtp == false) {
            Status status = new Status();
            status.setCode(4);
            status.setErrorCode("9");
            status.setErrorMessage("Invalid OTP");
            authenticateduser.setStatus(status);

            Data data = new Data();
            data.setValid(true);
            authenticateduser.setData(data);
        } else {
            Status status = new Status();
            status.setCode(0);
            status.setErrorCode("null");
            status.setErrorMessage("null");
            authenticateduser.setStatus(status);

            Data data = new Data();
            data.setValid(false);
            authenticateduser.setData(data);
        }
        return authenticateduser;
    }


}
