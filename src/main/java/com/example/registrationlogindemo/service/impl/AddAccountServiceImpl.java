package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.*;
import com.example.registrationlogindemo.entity.BankAccount;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.BankAccountRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.registrationlogindemo.service.AddAccountService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddAccountServiceImpl implements AddAccountService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Override
    public AuthenticateResponseDto checkUsernamePassword(AccountDto acc) {
        User user = userRepo.findByPhoneNoAndPassword(acc.getUsername(), acc.getPassword());
        AuthenticateResponseDto authenticateduser = new AuthenticateResponseDto();

        if (user == null) {
            AuthenticationStatus status = new AuthenticationStatus();
            status.setCode(4);
            status.setErrorCode("11");
            status.setError("The username or password is incorrect");
            status.setWarning("");
            status.setMessage("");
            authenticateduser.setStatus(status);

            AuthenticateData data = new AuthenticateData();
            data.setId_token("");
            authenticateduser.setData(data);


        } else {
            AuthenticationStatus status = new AuthenticationStatus();
            status.setCode(0);
            authenticateduser.setStatus(status);

            AuthenticateData data = new AuthenticateData();
            data.setId_token("eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlVTRVIiLCJBRE1JTiJdLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzczMTk3MiwiZXhwIjoxNzA3NzMyMjcyfQ.vspr2zYxzWhNVyfcYwrf83udasn8kT01t47561elUVIGykM8zG77vsnFvjONmUNhu3Oi29WIMWSgC6xehtKEyg");

            authenticateduser.setData(data);


        }
        return authenticateduser;
    }

    @Override
    public VerifyOtpResponseDto checkOtp(String otp) {
        Boolean checkOtp = true;
        VerifyOtpResponseDto authenticateduser = new VerifyOtpResponseDto();

        if (checkOtp == false) {
            VerifyOtpStatus status = new VerifyOtpStatus(4,"9","Invalid OTP");
            authenticateduser.setStatus(status);

            VerifyOtpData data = new VerifyOtpData();
            data.setIsValid(true);
            authenticateduser.setData(data);
        } else {
            VerifyOtpStatus status = new VerifyOtpStatus();
            status.setCode(0);
            authenticateduser.setStatus(status);

            VerifyOtpData data = new VerifyOtpData();
            data.setIsValid(true);
            authenticateduser.setData(data);
        }
        return authenticateduser;
    }

    @Override
    public CheckAccountNoResponseDto checkAccountNo(long accNo) {
        BankAccount checkAccount = bankAccountRepo.findByAccNo(accNo);
        CheckAccountNoResponseDto authenticateduser = new CheckAccountNoResponseDto();

        if (checkAccount == null) {
            CheckAccountNoStatus status = new CheckAccountNoStatus(4,"9","Invalid OTP");
            authenticateduser.setStatus(status);

            CheckAccountNoData data = new CheckAccountNoData();
            data.setRequireChangePassword(true);
            authenticateduser.setData(data);
        } else {
            CheckAccountNoStatus status = new CheckAccountNoStatus();
            status.setCode(0);
            authenticateduser.setStatus(status);

            CheckAccountNoData data = new CheckAccountNoData();
            data.setRequireChangePassword(false);
            authenticateduser.setData(data);
        }
        return authenticateduser;
    }

    @Override
    public AccountDetailsResponseDto getAccountDetails(long accNo) {
        BankAccount account= bankAccountRepo.findByAccNo(accNo);
        AccountDetailsResponseDto accountDetailsDto = new AccountDetailsResponseDto();

        if (account == null) {
            AuthenticationStatus status = new AuthenticationStatus(4,"9","Invalid JWT token","","");
            accountDetailsDto.setStatus(status);

            AccountDetailsData data = new AccountDetailsData();

            accountDetailsDto.setData(data);
        } else {
            AuthenticationStatus status = new AuthenticationStatus();
            status.setCode(0);
            accountDetailsDto.setStatus(status);

            AccountDetailsData data = new AccountDetailsData();
            data.setAccountId(account.getAccountId());
            data.setAccountName(account.getAccountName());
            data.setName(account.getUser().getName());
            data.setBankName(account.getBank().getBankName());
            data.setEmail(null);
            data.setPhone(account.getUser().getPhoneNo());
            data.setFrozen(account.isFrozen());
            data.setKycStatus(account.getKycStatus());
            data.setAccountStatus(account.getAccountStatus());
            accountDetailsDto.setData(data);
        }
        return accountDetailsDto;
    }

    @Override
    public AccountDetailsDto getAccountOverview(long accNo) {
        BankAccount account= bankAccountRepo.findByAccNo(accNo);
        AccountDetailsDto data= new AccountDetailsDto();

            data.setAccountNo(String.valueOf(account.getAccNo()));
            data.setName(account.getUser().getName());
            data.setPhoneNo(account.getUser().getPhoneNo());
            data.setType(account.getType());
            data.setCurrency(account.getCurrency());
            data.setKycStatus(account.getKycStatus());
            data.setCountry(account.getCountry());
            data.setBalance(String.valueOf(account.getBalance()));
            data.setLimit(account.getAccLimit());
            data.setAccountStatus(account.getAccountStatus());


        return data;
    }

    @Transactional
    @Override
    public void deleteBankAccount(long accNo) {
        if (bankAccountRepo.existsById(accNo)) {
            bankAccountRepo.deleteById(accNo);
        } else {
            throw new IllegalArgumentException("Account with id " + accNo + " does not exist.");
        }
    }
}
