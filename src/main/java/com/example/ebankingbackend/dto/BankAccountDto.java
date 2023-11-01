package com.example.ebankingbackend.dto;

import com.example.ebankingbackend.models.AccountStatus;

import java.util.Date;
@Data
public class BankAccountDto {
    private String id;
    private double balance;
    private Date createdAt;

    private AccountStatus status;
    private CustomerDTO customerDto;
}
