package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.AccountDto;
import com.example.registrationlogindemo.service.AddAccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private AddAccountService addAccountService;

    public AuthController(AddAccountService addAccountService) {
        this.addAccountService = addAccountService;
    }

    @GetMapping("/bankwallet")
    public String showBankWallet(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "bankwallet";
    }
    @GetMapping("/addacc")
    public String showAddAccountForm(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "addacc";
    }
    // handler method to handle register user form submit request
    @PostMapping("/addacc/save")
    public String registration(@Valid @ModelAttribute("user") AccountDto acc,
                               BindingResult result,
                               Model model){


        if (result.hasErrors()) {
            model.addAttribute("user", acc);
            return "addacc";
        }
        addAccountService.addAccount(acc);
        System.out.println(acc.getUsername());
        return "verifyOTP";
    }
    @GetMapping("/accOverview")
    public String showAccountOverview(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "accOverview";
    }
    @GetMapping("/accDetails")
    public String showAccountDetails(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "accDetails";
    }

}