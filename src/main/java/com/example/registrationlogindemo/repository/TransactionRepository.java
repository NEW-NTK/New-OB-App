package com.example.registrationlogindemo.repository;


import com.example.registrationlogindemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}