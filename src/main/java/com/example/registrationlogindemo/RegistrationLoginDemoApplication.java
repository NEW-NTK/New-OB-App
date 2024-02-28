package com.example.registrationlogindemo;

import com.example.registrationlogindemo.entity.Bank;
import com.example.registrationlogindemo.entity.BankAccount;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.BankAccountRepository;
import com.example.registrationlogindemo.repository.BankRepository;
import com.example.registrationlogindemo.repository.TransactionRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RegistrationLoginDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationLoginDemoApplication.class, args);
	}

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BankAccountRepository bankAccountRepo;

	@Autowired
	private TransactionRepository transactionRepos;

	@Autowired
	private BankRepository bankRepo;

	@Override
	public void run(String... args) throws Exception {
//		Bank bank1 = new Bank();
//		bank1.setBankName("XYZ Bank");
//		bankRepo.save(bank1);
//
//		User user1 = new User();
//		user1.setName("Kasuni");
//		user1.setPhoneNo("0767830229");
//		user1.setPassword("kasuni123");
//		userRepo.save(user1);
//
//		List<BankAccount> listBankAcc = new ArrayList<>();
//		BankAccount bankacc1 = new BankAccount();
//		bankacc1.setAccNo(123456789012L);
//		bankacc1.setType("Dollar");
//		bankacc1.setCurrency("USD");
//		bankacc1.setAccountStatus("Activated");
//		bankacc1.setCountry("Cambodia");
//		bankacc1.setBalance(25000.0F);
//		bankacc1.setAccLimit("1");
//		bankacc1.setAccountId("kasuni@xyz");
//		bankacc1.setAccountName("KasuniD");
//		bankacc1.setFrozen(true);
//
//		bankacc1.setBank(bank1);
//		bankacc1.setUser(user1);
//		listBankAcc.add(bankacc1);
//		bankAccountRepo.save(bankacc1);

//		Bank bank2 = new Bank();
//		bank2.setBankName("Wing(Cambodia) Bank");
//		bankRepo.save(bank2);
//
//		User user2 = new User();
//		user2.setName("Nethmi");
//		user2.setPhoneNo("0715566521");
//		user2.setPassword("nethmi123");
//		userRepo.save(user2);
//
//		List<BankAccount> listBankAcc2 = new ArrayList<>();
//		BankAccount bankacc2 = new BankAccount();
//		bankacc2.setAccNo(569871235642L);
//		bankacc2.setType("Dollar");
//		bankacc2.setCurrency("USD");
//		bankacc2.setAccountStatus("Activated");
//		bankacc2.setCountry("Cambodia");
//		bankacc2.setBalance(15000.0F);
//		bankacc2.setAccLimit("1");
//		bankacc2.setAccountId("nethmi@xyz");
//		bankacc2.setAccountName("NethmiW");
//		bankacc2.setFrozen(true);
//
//		bankacc2.setBank(bank2);
//		bankacc2.setUser(user2);
//		listBankAcc2.add(bankacc2);
//		bankAccountRepo.save(bankacc2);


	}
}
