package com.example.ebankingbackend.services.impl;

import com.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exceptions.CustomerNotFoundException;
import com.example.ebankingbackend.models.*;
import com.example.ebankingbackend.repository.AccountOperationRepository;
import com.example.ebankingbackend.repository.BankAccountRepository;
import com.example.ebankingbackend.repository.CustomerRepository;
import com.example.ebankingbackend.services.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
   private CustomerRepository customerRepository;
    @Autowired
   private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("hello my new customer");
        Customer newcustomer= customerRepository.save(customer);
        return newcustomer;
    }

    @Override
    public CurrentAccount createCURBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer== null){
            throw new CustomerNotFoundException("Customer not found");
        }

        CurrentAccount bnAccount =new CurrentAccount();

        bnAccount.setId(UUID.randomUUID().toString());
        bnAccount.setCreatedAt(new Date());
        bnAccount.setBalance(initialBalance);
        bnAccount.setOverDraft(overDraft);
        bnAccount.setCustomer(customer);
        CurrentAccount savedAccount= bankAccountRepository.save(bnAccount);
        return savedAccount;
    }

    @Override
    public SavingAccount createSAVBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer== null){
            throw new CustomerNotFoundException("Customer not found");
        }

        SavingAccount bnAccount =new SavingAccount();

        bnAccount.setId(UUID.randomUUID().toString());
        bnAccount.setCreatedAt(new Date());
        bnAccount.setBalance(initialBalance);
        bnAccount.setInterestRate(interestRate);
        bnAccount.setCustomer(customer);
        SavingAccount savedAccount= bankAccountRepository.save(bnAccount);
        return savedAccount;
    }



    @Override
    public List<Customer> listCustomer() {

        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bnAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccountNotFound"));

        return bnAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bnAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccountNotFound"));
        if(bnAccount.getBalance()<amount ){
            throw new BalanceNotSufficientException("Solde est insufficent");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bnAccount.setBalance(bnAccount.getBalance()-amount);
        bankAccountRepository.save(bnAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description)throws BankAccountNotFoundException {
        BankAccount bnAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccountNotFound"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bnAccount.setBalance(bnAccount.getBalance()+amount);
        bankAccountRepository.save(bnAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount)throws BankAccountNotFoundException {
  debit (accountIdSource,amount,"transfer to");
  credit(accountIdDestination,amount,"transfer from");
    }
    @Override
     public List<BankAccount> findAllBankAccount (){
        return bankAccountRepository.findAll();
    }
}
