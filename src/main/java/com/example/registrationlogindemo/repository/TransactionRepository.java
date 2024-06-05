package com.example.registrationlogindemo.repository;


import com.example.registrationlogindemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}