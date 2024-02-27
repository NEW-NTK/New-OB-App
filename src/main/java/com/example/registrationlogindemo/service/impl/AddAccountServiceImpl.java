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
@Service
public class AddAccountServiceImpl implements AddAccountService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public AuthenticateResponseDto checkUsernamePassword(AccountDto acc){
       User user = userRepo.findByPhoneNoAndPassword(acc.getUsername(), acc.getPassword());
       AuthenticateResponseDto authenticateduser = new AuthenticateResponseDto();

       if(user == null) {
           authenticateduser.setStatus(new Status(4, "11", "The username or password is incorrect", "", ""));
           authenticateduser.setData(new Data(""));
       }
       else{
           authenticateduser.setStatus(new Status(0, "null", "null", "null", "null"));
           authenticateduser.setData(new Data("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlVTRVIiLCJBRE1JTiJdLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzczMTk3MiwiZXhwIjoxNzA3NzMyMjcyfQ.vspr2zYxzWhNVyfcYwrf83udasn8kT01t47561elUVIGykM8zG77vsnFvjONmUNhu3Oi29WIMWSgC6xehtKEyg"));
       }
       return authenticateduser;
    }
}
