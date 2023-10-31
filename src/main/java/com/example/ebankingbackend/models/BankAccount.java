package com.example.ebankingbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    // cad dans la classe accountoperation il ya un cle etrangere qui s appelle bankaccount
    //cette annotation il est mapper/representer dans la table acountoperation avec la cle etrangere bank ccount
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;



}
