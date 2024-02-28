package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.*;
import com.example.registrationlogindemo.entity.BankAccount;
import com.example.registrationlogindemo.entity.Transaction;
import com.example.registrationlogindemo.repository.BankAccountRepository;
import com.example.registrationlogindemo.service.AddAccountService;
import com.example.registrationlogindemo.service.CheckBakongUserHasBankService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
public class AuthController {

    @Autowired
    private AddAccountService addAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepo;

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
    public String showBankWallet(@ModelAttribute("accountDetails") AccountDetailsDto accountDetails,@PathVariable String status, Model model){
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
    public ResponseEntity<Map<String, String>>  selectBank(@RequestBody AddAccountBankNameDto bank, HttpSession session) {
        System.out.println("Received Bank Object: " + bank.getBankname());
        Map<String, String> response = new HashMap<>();
        response.put("page", "addacc");
        session.setAttribute("bankname", bank.getBankname());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/addacc")
    public String showAddAccountForm(Model model,HttpSession session){
        AccountDto acc = new AccountDto();
        model.addAttribute("user", acc);
//        model.addAttribute("bankname", bankname );
        model.addAttribute("bankname", session.getAttribute("bankname"));
        return "addacc";
    }

    @PostMapping("/addacc/checkuser")
    public ResponseEntity<AuthenticateResponseDto> registration(@Valid @RequestBody AccountDto acct, HttpSession session,Model model){

        session.setAttribute("username", acct.getUsername());
        session.setAttribute("password", acct.getPassword());
        System.out.println("login username " +acct.getUsername());
        AuthenticateResponseDto authenticateResponseDto =addAccountService.checkUsernamePassword(acct);

        return ResponseEntity.ok(authenticateResponseDto);

    }
    @GetMapping("/verifyOtp")
    public String showVerifyOtp(Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("password", session.getAttribute("password"));
        model.addAttribute("bankname", session.getAttribute("bankname"));

        return "verifyOTP";
    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<VerifyOtpResponseDto> checkPhnNo(@RequestBody String otp,HttpSession session){

        VerifyOtpResponseDto authenticateResponseDto =addAccountService.checkOtp(otp);
        System.out.println("OTP :"+ otp);
        return new ResponseEntity<>(authenticateResponseDto, HttpStatus.FOUND);
    }
    @GetMapping("/addBankAccount")
    public String showAddBankAccount(Model model,HttpSession session){

        AccountDetailsDto accountDetails= new AccountDetailsDto();
        model.addAttribute("accountDetails", accountDetails);

        model.addAttribute("bankname", session.getAttribute("bankname"));
        return "addBankAccount";
    }


    @PostMapping("/VerifyAccountNo")
    public ResponseEntity<CheckAccountNoResponseDto> VerifyAccountNo(@RequestBody String AccNo,HttpSession session){
        session.setAttribute("Account No", AccNo);
        CheckAccountNoResponseDto authenticateResponseDto =addAccountService.checkAccountNo(Long.parseLong(AccNo));
        System.out.println("Account No :"+ AccNo);
        return ResponseEntity.ok(authenticateResponseDto);
    }
    @GetMapping ("/bankwallet/hasbankaccount")
    public String bankWalletAfterTransaction(Model model, HttpSession session){

        model.addAttribute("Account No", session.getAttribute("Account No"));
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        Object accountNumberObj = session.getAttribute("Account No");

        if (accountNumberObj instanceof Long) {

            Long accountNumber = (Long) accountNumberObj;
            accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
        } else if (accountNumberObj instanceof String) {

            String accountNumberStr = (String) accountNumberObj;
            try {
                Long accountNumber = Long.parseLong(accountNumberStr);
                accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing account number: " + e.getMessage());
            }
        } else {
            System.err.println("Unexpected type for account number: " + accountNumberObj.getClass().getName());
        }

        boolean hideUIContainer = false;
        model.addAttribute("hideUIContainer", hideUIContainer);
        model.addAttribute("type",accountDetailsDto.getType() );
        model.addAttribute("balance", accountDetailsDto.getBalance());
        model.addAttribute("bankname", session.getAttribute("bankname"));


        return "bankwallet";
    }

    @GetMapping ("/findByAccountName/{accountNumber}")
    public  ResponseEntity<AccountDetailsResponseDto> GetuserProfileByAccount(@PathVariable String accountNumber){

        System.err.println(accountNumber);
        AccountDetailsResponseDto accountDetailsResponseDto =addAccountService.getAccountDetails(Long.parseLong(accountNumber));

        return new ResponseEntity<>(accountDetailsResponseDto, HttpStatus.FOUND);
    }
    @GetMapping("/accDetails")
    public String showAccountDetails(Model model, HttpSession session){
        model.addAttribute("Account No", session.getAttribute("Account No"));
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        Object accountNumberObj = session.getAttribute("Account No");

        if (accountNumberObj instanceof Long) {

            Long accountNumber = (Long) accountNumberObj;
            accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
        } else if (accountNumberObj instanceof String) {

            String accountNumberStr = (String) accountNumberObj;
            try {
                Long accountNumber = Long.parseLong(accountNumberStr);
                accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing account number: " + e.getMessage());
            }
        } else {
            System.err.println("Unexpected type for account number: " + accountNumberObj.getClass().getName());
        }
        model.addAttribute("accountNo",accountDetailsDto.getAccountNo() );
        model.addAttribute("name", accountDetailsDto.getName());
        model.addAttribute("phoneNo", accountDetailsDto.getPhoneNo());
        model.addAttribute("type",accountDetailsDto.getType() );
        model.addAttribute("currency",accountDetailsDto.getCurrency() );
        model.addAttribute("accountStatus", accountDetailsDto.getAccountStatus());
        model.addAttribute("kycStatus",accountDetailsDto.getKycStatus());
        model.addAttribute("country",accountDetailsDto.getCountry());
        model.addAttribute("balance", accountDetailsDto.getBalance());
        model.addAttribute("limit",accountDetailsDto.getLimit());
        model.addAttribute("bankname", session.getAttribute("bankname"));
        return "accDetails";
    }

    @GetMapping("/accOverview")
    public String showAccountOverview(Model model, HttpSession session){
        model.addAttribute("Account No", session.getAttribute("Account No"));
        AccountDetailsDto accountDetailsDto = new AccountDetailsDto();
        Object accountNumberObj = session.getAttribute("Account No");

        if (accountNumberObj instanceof Long) {

            Long accountNumber = (Long) accountNumberObj;
            accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
        } else if (accountNumberObj instanceof String) {

            String accountNumberStr = (String) accountNumberObj;
            try {
                Long accountNumber = Long.parseLong(accountNumberStr);
                accountDetailsDto = addAccountService.getAccountOverview(accountNumber);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing account number: " + e.getMessage());
            }
        } else {
            System.err.println("Unexpected type for account number: " + accountNumberObj.getClass().getName());
        }

        model.addAttribute("type",accountDetailsDto.getType() );
        model.addAttribute("balance", accountDetailsDto.getBalance());
        model.addAttribute("bankname", session.getAttribute("bankname"));


        return "accOverview";
    }
    //***********************************************************
    @GetMapping("/cashDeposit")
    public String showBankToDeposit(){
        return "cashDeposit";
    }

    @PostMapping("/cashDeposit")
    public ResponseEntity<Map<String, String>>  selectCashDepositBank(Model model, @RequestBody String bankname,HttpSession session) {

        session.setAttribute("DepositBank", bankname);
        Map<String, String> response = new HashMap<>();
        response.put("page", "addAccNumber");
       return ResponseEntity.ok(response);
    }
    @GetMapping("/addAccNumber")
    public String addAccountNumber(Model model,HttpSession session){

        model.addAttribute("depositbankname", session.getAttribute("DepositBank"));

        return "addAccNumber";
    }

    @PostMapping("/setAccNumber")
    public ResponseEntity<Map<String, String>>  setaccountNumber(Model model, @RequestBody String AccNumber,HttpSession session){
        session.setAttribute("depositAccNumber", AccNumber);


        Map<String, String> response = new HashMap<>();
        response.put("page", "setAmount");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/setAmount")
    public String setAccountNumber(Model model,HttpSession session){
        BankAccount checkAccount = new BankAccount();
        Object accountNumberObj = session.getAttribute("depositAccNumber");

        if (accountNumberObj instanceof Long) {

            Long accountNumber = (Long) accountNumberObj;
            checkAccount = bankAccountRepo.findByAccNo(accountNumber);
        } else if (accountNumberObj instanceof String) {

            String accountNumberStr = (String) accountNumberObj;
            try {
                Long accountNumber = Long.parseLong(accountNumberStr);
               checkAccount = bankAccountRepo.findByAccNo(accountNumber);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing account number: " + e.getMessage());
            }
        } else {
            System.err.println("Unexpected type for account number: " + accountNumberObj.getClass().getName());
        }

        if(checkAccount != null){
            model.addAttribute("recieverName",checkAccount.getUser().getName() );
        }
        model.addAttribute("depositbankname", session.getAttribute("DepositBank"));

        return "setamount";
    }


    @PostMapping("/setAmount")
    public String setDepositAmount(Model model, @RequestBody String amount,HttpSession session){
        session.setAttribute("depositAmount", amount);

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

//    @PostMapping("/bankwallet/hasbankaccount")
//    public String bankWalletAfterTransaction(@ModelAttribute("transaction") TransactionDto trans,@ModelAttribute("accountDetails") AccountDetailsDto accountDetails,@RequestParam String bankname,
//                                             BindingResult result,
//                                             Model model){
//        // Assuming you have some logic to retrieve account details from the backend
////        List<AccountDetailsDto> accountDetailsList = getAccountDetailsList();
//        accountDetails.setType("Dollar Account");
//        accountDetails.setBalance("$1,000.00");
//
//        addBankAccountService.addBankAccount(accountDetails,bankname);
//        // Pass the accountDetailsList and bankname to the Thymeleaf template
////        model.addAttribute("accountDetailsList", accountDetailsList);
//        System.out.println("\nAccount details after go to bankwallet \n" +"AccountNo:" + accountDetails.getAccountNo() +"\n" +"name :"+ accountDetails.getName() +"\n"+ "phoneNo :"+ accountDetails.getPhoneNo() +"\n"+ "type :"+ accountDetails.getType()  +"\n"+ "currency :"+ accountDetails.getCurrency() +"\n"+ "accountStatus :"+ accountDetails.getAccountStatus()+"\n"+ "kycStatus :"+ accountDetails.getKycStatus()+"\n"+ "country :"+ accountDetails.getCountry()+"\n"+ "balance :"+ accountDetails.getBalance()+"\n"+ "limit :"+ accountDetails.getLimit()  );
//        model.addAttribute("bankName", bankname);
//        System.out.println("\nTransaction details after go to bankwallet \n" +"RecepientBank :" + trans.getRecepientBank() +"\n" +"Receiver AccNO :"+ trans.getDestinationAccNumber() +"\n"+ "Receiver Name :"+ trans.getRecieverName() +"\n"+ "Amount:"+ trans.getAmount()  +"\n"+ "Description:"+ trans.getDescription()    );
//        model.addAttribute("bankname", trans.getRecepientBank());
//        model.addAttribute("accountDetails", accountDetails);
//        boolean hideUIContainer = false;
//        model.addAttribute("hideUIContainer", hideUIContainer);
//        return "bankwallet";
//    }





}