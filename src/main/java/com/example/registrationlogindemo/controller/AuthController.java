package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.AccountDto;
import com.example.registrationlogindemo.dto.BakongUser;
import com.example.registrationlogindemo.dto.BankNameDto;
import com.example.registrationlogindemo.service.AddAccountService;
import com.example.registrationlogindemo.service.CheckBakongUserHasBankService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class AuthController {

    @Autowired
    private AddAccountService addAccountService;

    @Autowired
    private CheckBakongUserHasBankService checkBakongUserHasBankService;

    public AuthController(AddAccountService addAccountService) {
        this.addAccountService = addAccountService;
    }

    @GetMapping("/bakong")
    public String showFirstScreen(){
        return "firstscreen";
    }

    @GetMapping("/authorize")
    public String showFingerPrint(){
        return "fingerprint";
    }

    @PostMapping("/authorize")
    public ResponseEntity<Map<String, String>> userHasBankAccount(@RequestBody BakongUser user) {
        System.out.println("Received Bank user: " + user.getStatus());
        boolean checked = checkBakongUserHasBankService.checkUserHasBank(user);
        System.out.println(checked);

        Map<String, String> response = new HashMap<>();
        response.put("page", "bankwallet");
        response.put("status", String.valueOf(checked));

        return ResponseEntity.ok(response);
    }
    @GetMapping("/bankwallet/{status}")
    public String showBankWallet(@PathVariable String status, Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        boolean hideUIContainer = "nobankaccount".equals(status);
        model.addAttribute("hideUIContainer", hideUIContainer);
        return "bankwallet";
    }

    @GetMapping("/selectbank")
    public String showSelectBank(Model model){
        BankNameDto bankname = new BankNameDto();
        model.addAttribute("bank", bankname);
        return "selectbank";
    }


    @PostMapping("/selectbank/bank")
    public ResponseEntity<String> selectBank(@RequestBody BankNameDto bank) {
        System.out.println("Received Bank Object: " + bank.getBankname());
    
        return ResponseEntity.ok("addacc");
    }

    @GetMapping("/addacc")
    public String showAddAccountForm(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "addacc";
    }
    // handler method to handle register user form submit request
    @PostMapping("/addacc/verifyOTP")
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
//    @PostMapping("/addacc/verifyOTP")
//    public String otpValidation(@Valid @ModelAttribute("user") AccountDto acc,
//                               BindingResult result,
//                               Model model){
//
//
//        if (result.hasErrors()) {
//            model.addAttribute("user", acc);
//            return "addacc";
//        }
//        addAccountService.addAccount(acc);
//        System.out.println(acc.getUsername());
//        return "verifyOTP";
//    }

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
    @GetMapping("/addAccNumber")
    public String addAccountNumber(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "addAccNumber";
    }
    @GetMapping("/setamount")
    public String setAmount(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "setamount";
    }
    @GetMapping("/confirmation")
    public String confirmation(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "confirmation";
    }

    @GetMapping("/moneyTransfered")
    public String moneyTransfered(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "moneyTransfered";
    }

    @GetMapping("/selectBank")
    public String showSearchBank(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "selectBank";
    }

    @GetMapping("/cashDeposit")
    public String showBankToDeposit(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "cashDeposit";
    }

}