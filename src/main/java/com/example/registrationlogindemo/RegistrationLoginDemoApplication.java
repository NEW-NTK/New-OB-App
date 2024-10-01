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
		Bank bank1 = new Bank();
		bank1.setBankName("XYZ Bank");
		bankRepo.save(bank1);

		User user1 = new User();
		user1.setName("Kasuni");
		user1.setPhoneNo("0767830229");
		user1.setPassword("kasuni123");
		userRepo.save(user1);

		List<BankAccount> listBankAcc = new ArrayList<>();
		BankAccount bankacc1 = new BankAccount();
		bankacc1.setAccNo(123456789012L);
		bankacc1.setType("Dollar");
		bankacc1.setCurrency("USD");
		bankacc1.setAccountStatus("Activated");
		bankacc1.setCountry("Cambodia");
		bankacc1.setBalance(25000.0F);
		bankacc1.setAccLimit("1");
		bankacc1.setAccountId("kasuni@xyz");
		bankacc1.setAccountName("KasuniD");
		bankacc1.setFrozen(true);

		bankacc1.setBank(bank1);
		bankacc1.setUser(user1);
		listBankAcc.add(bankacc1);
		bankAccountRepo.save(bankacc1);

		Bank bank2 = new Bank();
		bank2.setBankName("Wing(Cambodia) Bank");
		bankRepo.save(bank2);

		User user2 = new User();
		user2.setName("Nethmi");
		user2.setPhoneNo("0715566521");
		user2.setPassword("nethmi123");
		userRepo.save(user2);

		List<BankAccount> listBankAcc2 = new ArrayList<>();
		BankAccount bankacc2 = new BankAccount();
		bankacc2.setAccNo(569871235642L);
		bankacc2.setType("Dollar");
		bankacc2.setCurrency("USD");
		bankacc2.setAccountStatus("Activated");
		bankacc2.setCountry("Cambodia");
		bankacc2.setBalance(15000.0F);
		bankacc2.setAccLimit("1");
		bankacc2.setAccountId("nethmi@xyz");
		bankacc2.setAccountName("NethmiW");
		bankacc2.setFrozen(true);

		bankacc2.setBank(bank2);
		bankacc2.setUser(user2);
		listBankAcc2.add(bankacc2);
		bankAccountRepo.save(bankacc2);


		Bank bank3 = bankRepo.findByBankName("XYZ Bank");
		if (bank3 == null) {
			// If bank doesn't exist, create a new one
			bank3 = new Bank();
			bank3.setBankName("XYZ Bank");
			bankRepo.save(bank3);
		}

		User user3 = new User();
		user3.setName("John Doe");
		user3.setPhoneNo("+855 23965741");
		user3.setPassword("john123");
		userRepo.save(user3);

		List<BankAccount> listBankAcc3 = new ArrayList<>();
		BankAccount bankacc3 = new BankAccount();
		bankacc3.setAccNo(212006653412L);
		bankacc3.setType("Dollar");
		bankacc3.setCurrency("USD");
		bankacc3.setAccountStatus("Activated");
		bankacc3.setCountry("Cambodia");
		bankacc3.setBalance(15000.0F);
		bankacc3.setAccLimit("1");
		bankacc3.setAccountId("john@xyz");
		bankacc3.setAccountName("JohnD");
		bankacc3.setFrozen(true);

		bankacc3.setBank(bank3);
		bankacc3.setUser(user3);
		listBankAcc3.add(bankacc3);
		bankAccountRepo.save(bankacc3);


		Bank bank4 = bankRepo.findByBankName("XYZ Bank");
      if (bank4 == null) {
			// If bank doesn't exist, create a new one
		  bank4 = new Bank();
		  bank4.setBankName("XYZ Bank");
			bankRepo.save(bank4);
		}
		User user4 = new User();
		user4.setName("Jane Doe");

		user4.setPhoneNo("+855 961234567");
		user4.setPassword("jane123");
		userRepo.save(user4);
		List<BankAccount> listBankAcc4 = new ArrayList<>();
		BankAccount bankacc4 = new BankAccount();
		bankacc4.setAccNo(215896347512L);
		bankacc4.setType("Dollar");
		bankacc4.setCurrency("USD");
		bankacc4.setAccountStatus("Activated");
		bankacc4.setCountry("Cambodia");
		bankacc4.setBalance(15000.0F);
		bankacc4.setAccLimit("1");
		bankacc4.setAccountId("jane@xyz");
		bankacc4.setAccountName("JaneD");
		bankacc4.setFrozen(true);

		bankacc4.setBank(bank4);
		bankacc4.setUser(user4);
		listBankAcc4.add(bankacc4);
		bankAccountRepo.save(bankacc4);
	}
}
