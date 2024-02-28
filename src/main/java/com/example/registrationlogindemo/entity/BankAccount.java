package com.example.registrationlogindemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bankAccounts")
public class BankAccount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private long accNo;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private boolean frozen;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private  String accountStatus;

    @Column
    private String kycStatus;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private float balance;

    @Column(nullable = false)
    private String accLimit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonBackReference
    private Bank bank;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Transaction> sourceTransactions;

    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Transaction> destinationTransactions;


}
