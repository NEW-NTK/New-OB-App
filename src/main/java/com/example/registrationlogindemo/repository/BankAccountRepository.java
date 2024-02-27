package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
