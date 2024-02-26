package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.*;
import com.example.registrationlogindemo.service.AddAccountService;
import com.example.registrationlogindemo.service.AddBankAccountService;
import com.example.registrationlogindemo.service.CheckBakongUserHasBankService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private  AddBankAccountService addBankAccountService;

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
    public String showBankWallet(@PathVariable String status, Model model,@ModelAttribute("accountDetails") AccountDetailsDto accountDetails){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        boolean hideUIContainer = "nobankaccount".equals(status);
        model.addAttribute("hideUIContainer", hideUIContainer);
        return "bankwallet";
    }

    @GetMapping("/selectbank")
    public String showSelectBank(Model model){
        AddAccountBankNameDto bankname = new AddAccountBankNameDto();
        model.addAttribute("bank", bankname);
        return "selectBank";
    }

    @PostMapping("/selectbank/bank")
    public ResponseEntity<Map<String, String>>  selectBank(@RequestBody AddAccountBankNameDto bank) {
        System.out.println("Received Bank Object: " + bank.getBankname());
        Map<String, String> response = new HashMap<>();
        response.put("page", "addacc");
        response.put("bankname", bank.getBankname());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/addacc/{bankname}")
    public String showAddAccountForm(@PathVariable String bankname,Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);
        model.addAttribute("bankname", bankname );
        return "addacc";
    }
    // handler method to handle register user form submit request
    @PostMapping("/addacc/verifyOTP")
    public String registration(@Valid @ModelAttribute("user") AccountDto acc,@RequestParam String bankname,
                               BindingResult result,
                               Model model){


        if (result.hasErrors()) {
            model.addAttribute("user", acc);
            return "addacc";
        }
        addAccountService.addAccount(acc);
        System.out.println(acc.getUsername());
        model.addAttribute("phoneNo", acc.getUsername());
        model.addAttribute("bankname", bankname );
        System.out.println("bank name:verify otpp:"+bankname);
        return "verifyOTP";
    }
    @PostMapping("/checkPhnNo")
    public ResponseEntity<Map<String, String>> checkPhnNo(@RequestBody AccountDto loginObject,@RequestParam String bankname,
                               BindingResult result,
                               Model model){

        System.out.println("login username " +loginObject.getUsername());
        System.out.println("login password "+loginObject.getPassword());
        System.out.println("bank name from verifyotp page "+bankname);

        Map<String, String> response = new HashMap<>();
        response.put("page", "addBankAccount");
        response.put("bankname", bankname);
        return ResponseEntity.ok(response);
//          return ResponseEntity.ok("addBankAccount");
    }
    @GetMapping("/addBankAccount/{bankname}")
    public String showAddBankAccount(@PathVariable String bankname,Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);
        AccountDetailsDto accountDetails= new AccountDetailsDto();
//        transaction.setRecepientBank(bankname);
        model.addAttribute("accountDetails", accountDetails);


        model.addAttribute("bankname", bankname );
        return "addBankAccount";
    }

    @GetMapping("/accOverview")
    public String showAccountOverview(Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);

        return "accOverview";
    }


    @GetMapping("/accDetails/{bankname}")
    public String showAccountDetails(@ModelAttribute("accountDetails") AccountDetailsDto accDetailsObject,Model model){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);
        accDetailsObject.setPhoneNo("0719219659");
        accDetailsObject.setName("Kim Chan");
        accDetailsObject.setCurrency("USD");
        accDetailsObject.setAccountStatus("Active");
        accDetailsObject.setKycStatus("---");
        accDetailsObject.setCountry("Cambodia");
        accDetailsObject.setLimit("---");
        System.out.println("account details"+ accDetailsObject.getAccountNo());
        return "accDetails";
    }
    @PostMapping("/accDetails")
    public ResponseEntity<Map<String, String>> accDetails(@RequestBody AccountDetailsDto accDetailsObject,@RequestParam String bankname,
                                                          BindingResult result,
                                                          Model model){

        System.out.println("account no :"+accDetailsObject.getAccountNo());
        System.out.println("type :"+accDetailsObject.getType());
        System.out.println("balance :"+accDetailsObject.getBalance());
//        System.out.println("bank name from verifyotp page "+accDetailsObject);
        model.addAttribute("accountDetails",accDetailsObject);
        Map<String, String> response = new HashMap<>();
        response.put("page", "accDetails");
        response.put("bankname", bankname);
        return ResponseEntity.ok(response);
//          return ResponseEntity.ok("addBankAccount");
    }
    @GetMapping("/cashDeposit")
    public String showBankToDeposit(){
        return "cashDeposit";
    }

    @PostMapping("/cashDeposit")
    public ResponseEntity<Map<String, String>> selectCashDepositBank(Model model, @RequestBody TransactionDto trans) {

        model.addAttribute("transaction", trans);
        System.out.println("Received Deposit Bank Object: " + trans.getRecepientBank());

        Map<String, String> response = new HashMap<>();
        response.put("page", "addAccNumber");
        response.put("bankname", trans.getRecepientBank());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/addAccNumber/{bankname}")
    public String addAccountNumber(@PathVariable String bankname, Model model){
        TransactionDto transaction= new TransactionDto();
        transaction.setRecepientBank(bankname);
        model.addAttribute("transaction", transaction);

        System.out.println("Received Deposit Bank Object in addaccount page: " +bankname);
        model.addAttribute("bankname", bankname);
        return "addAccNumber";
    }

    @PostMapping("/setDepositAmount")
    public String addaccountNumber(@ModelAttribute("transaction") TransactionDto trans,
                                   BindingResult result,
                                   Model model){
        if (123456789101L == trans.getDestinationAccNumber()) {
            // Set the receiver name as "Kim Chan"
            trans.setRecieverName("Kim Chan");
        }
        System.out.println("\nTransaction details after adding Account Number\n" +
                "RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );

        model.addAttribute("bankname", trans.getRecepientBank());

        return "setamount";
    }

    @PostMapping("/depositVerifyOTP")
    public String setDepositAmount(@ModelAttribute("transaction") TransactionDto trans,
                                   BindingResult result,
                                   Model model){
        System.out.println("\nTransaction details after adding amount \n" +"RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );
        model.addAttribute("transaction", trans );
        model.addAttribute("bankname", trans.getRecepientBank());
        return "DepositVerifyOTP";
    }


    @PostMapping("/confirmation")
    public String doneConfirm(@ModelAttribute("transaction") TransactionDto trans,
                                   BindingResult result,
                                   Model model){
        System.out.println("\nTransaction details after done confirmation \n" +"RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );
        model.addAttribute("transaction", trans );
        return "confirmation";
    }
    @PostMapping("/moneyTransfered")
    public String moneyTransfered(@ModelAttribute("transaction") TransactionDto trans,
                              BindingResult result,
                              Model model){
        model.addAttribute("transaction", trans );
        model.addAttribute("bankname", trans.getRecepientBank());

        System.out.println("\nTransaction details after moneytranfered \n" +"RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );

        return "moneyTransfered";
    }

    @PostMapping("/bankwallet/hasbankaccount")
    public String bankWalletAfterTransaction(@ModelAttribute("transaction") TransactionDto trans,@ModelAttribute("accountDetails") AccountDetailsDto accountDetails,@RequestParam String bankname,
                                  BindingResult result,
                                  Model model){
        // Assuming you have some logic to retrieve account details from the backend
//        List<AccountDetailsDto> accountDetailsList = getAccountDetailsList();
        accountDetails.setType("Dollar Account");
        accountDetails.setBalance("$1,000.00");

        addBankAccountService.addBankAccount(accountDetails,bankname);
        // Pass the accountDetailsList and bankname to the Thymeleaf template
//        model.addAttribute("accountDetailsList", accountDetailsList);
        System.out.println("\nAccount details after go to bankwallet \n" +"AccountNo:" + accountDetails.getAccountNo() +"\n" +"name :"+ accountDetails.getName() +"\n"+ "phoneNo :"+ accountDetails.getPhoneNo() +"\n"+ "type :"+ accountDetails.getType()  +"\n"+ "currency :"+ accountDetails.getCurrency() +"\n"+ "accountStatus :"+ accountDetails.getAccountStatus()+"\n"+ "kycStatus :"+ accountDetails.getKycStatus()+"\n"+ "country :"+ accountDetails.getCountry()+"\n"+ "balance :"+ accountDetails.getBalance()+"\n"+ "limit :"+ accountDetails.getLimit()  );
        model.addAttribute("bankName", bankname);
        System.out.println("\nTransaction details after go to bankwallet \n" +"RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );
        model.addAttribute("bankname", trans.getRecepientBank());
        model.addAttribute("accountDetails", accountDetails);
        boolean hideUIContainer = false;
        model.addAttribute("hideUIContainer", hideUIContainer);
        return "bankwallet";
    }



}