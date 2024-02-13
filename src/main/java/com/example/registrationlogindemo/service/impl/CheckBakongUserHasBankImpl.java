package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.BakongUser;
import com.example.registrationlogindemo.service.CheckBakongUserHasBankService;
import org.springframework.stereotype.Service;

@Service
public class CheckBakongUserHasBankImpl implements CheckBakongUserHasBankService {

    @Override
    public boolean checkUserHasBank(BakongUser user){
        System.out.println("Received Bank user in service class: " + user.getStatus());
        if(user.getBakongID() == 1 && user.getStatus().equals("yes")){
            return true;
        }
        return false;
    }
}
