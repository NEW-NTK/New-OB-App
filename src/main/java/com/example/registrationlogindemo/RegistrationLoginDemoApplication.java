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


	}
}
