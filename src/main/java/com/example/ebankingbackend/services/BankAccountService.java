package com.example.ebankingbackend.services;

import com.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exceptions.CustomerNotFoundException;
import com.example.ebankingbackend.models.BankAccount;
import com.example.ebankingbackend.models.CurrentAccount;
import com.example.ebankingbackend.models.Customer;
import com.example.ebankingbackend.models.SavingAccount;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer);
    public CurrentAccount createCURBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    public SavingAccount createSAVBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List <Customer> listCustomer();
    BankAccount getBankAccount (String accountId);
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount,String description) throws BankAccountNotFoundException;
    void transfert (String accountIdSource,String accountIdDestination, double amount);
     List<BankAccount> findAllBankAccount();



}
