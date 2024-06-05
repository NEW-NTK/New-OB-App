package com.example.registrationlogindemo.repository;


import com.example.registrationlogindemo.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByBankName(String bankName);

}
