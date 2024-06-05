package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.*;
import com.example.registrationlogindemo.entity.BankAccount;
import com.example.registrationlogindemo.entity.Transaction;
import com.example.registrationlogindemo.repository.BankAccountRepository;
import com.example.registrationlogindemo.service.AddAccountService;
import com.example.registrationlogindemo.service.CheckBakongUserHasBankService;
import com.example.registrationlogindemo.service.TransactionService;
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
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class AuthController {

    @Autowired
    private AddAccountService addAccountService;

    @Autowired
    private TransactionService transactionService;

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
    public ResponseEntity<VerifyOtpResponseDto> checkOtp(@RequestBody String otp,HttpSession session){

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
        model.addAttribute("AccountNo", session.getAttribute("Account No"));
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
    public ResponseEntity<Map<String, String>> setDepositAmount(Model model, @RequestBody Map<String, Object> amountDesc,HttpSession session){
//        session.setAttribute("depositAmount", amountDesc);
        Object amountObject = amountDesc.get("amount");
        Object descriptionObject = amountDesc.get("description");
        if (amountObject != null && descriptionObject != null) {
            // Convert to appropriate types if needed
            float amountValue = Integer.parseInt(amountObject.toString());
            String descriptionValue = descriptionObject.toString();

            System.out.println("Amount: " + amountValue);
            System.out.println("Description: " + descriptionValue);

            session.setAttribute("depositAmount", amountValue);
            session.setAttribute("description", descriptionValue);


        }

        Map<String, String> response = new HashMap<>();
        response.put("page", "DepositVerifyOTP");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/DepositVerifyOTP")
    public String GetDepositeVerifyOtpPage(Model model,HttpSession session){

        model.addAttribute("username", session.getAttribute("username"));

        return "DepositVerifyOTP";
    }

    @GetMapping("/confirmation")
    public String doneConfirm(Model model,HttpSession session){

        model.addAttribute("recepientBank", session.getAttribute("DepositBank"));
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

        model.addAttribute("recieverName", checkAccount.getUser().getName());
        model.addAttribute("destinationAccNumber", session.getAttribute("depositAccNumber"));
        model.addAttribute("amount", session.getAttribute("depositAmount"));
        model.addAttribute("Description", session.getAttribute("description"));

        return "confirmation";
    }
    @PostMapping("/moneyTransfered")
    public String moneyTransfered(HttpSession session,
                              Model model){
        model.addAttribute("recepientBank", session.getAttribute("DepositBank"));
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

        model.addAttribute("recieverName", checkAccount.getUser().getName());
        model.addAttribute("destinationAccNumber", session.getAttribute("depositAccNumber"));
        model.addAttribute("amount", session.getAttribute("depositAmount"));
        model.addAttribute("Description", session.getAttribute("description"));

        return "moneyTransfered";
    }
    @PostMapping("/finishTransaction")
    public ResponseEntity<FinishTransactionResponseDto> finishTransaction(HttpSession session, Model model) {
        FinishTransactionResponseDto finishTransactionResponseDto = new FinishTransactionResponseDto();
        Object depositAccNumberObj = session.getAttribute("depositAccNumber");
        Object accountNumberObj = session.getAttribute("Account No");
        Object depositAmountObj = session.getAttribute("depositAmount");

        Long depositAccNumber = null;
        Long accountNumber = null;
        Float depositAmount = null;

        if (depositAccNumberObj != null) {
            if (depositAccNumberObj instanceof Long) {
                depositAccNumber = (Long) depositAccNumberObj;
            } else if (depositAccNumberObj instanceof String) {
                depositAccNumber = Long.valueOf((String) depositAccNumberObj);
            }
        }

        if (accountNumberObj != null) {
            if (accountNumberObj instanceof Long) {
                accountNumber = (Long) accountNumberObj;
            } else if (accountNumberObj instanceof String) {
                accountNumber = Long.valueOf((String) accountNumberObj);
            }
        }

        if (depositAmountObj != null && depositAmountObj instanceof Float) {
            depositAmount = (Float) depositAmountObj;
        }
        // Call service method with session attributes
        finishTransactionResponseDto = transactionService.UpdateBalance(accountNumber, depositAccNumber, depositAmount);

//        model.addAttribute("recepientBank", session.getAttribute("DepositBank"));
//        model.addAttribute("destinationAccNumber", session.getAttribute("depositAccNumber"));
//        model.addAttribute("amount", session.getAttribute("depositAmount"));
//        model.addAttribute("Description", session.getAttribute("description"));

        return  new ResponseEntity<>(finishTransactionResponseDto,HttpStatus.OK);
    }



    @DeleteMapping("api/accounts/{accNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBankAccount(@PathVariable long accNo) {
        addAccountService.deleteBankAccount(accNo);
    }






}