package com.example.registrationlogindemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    @JsonManagedReference
    private BankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id", nullable = false)
    @JsonManagedReference
    private BankAccount destinationAccount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private float amount;
}
