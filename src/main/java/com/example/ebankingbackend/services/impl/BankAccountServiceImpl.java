package com.example.ebankingbackend.services.impl;

import com.example.ebankingbackend.dto.BankAccountDto;
import com.example.ebankingbackend.dto.CurrentBankAccountDto;
import com.example.ebankingbackend.dto.CustomerDTO;
import com.example.ebankingbackend.dto.SavingBankAccountDto;
import com.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exceptions.CustomerNotFoundException;
import com.example.ebankingbackend.mapers.BankAccountMapperImpl;
import com.example.ebankingbackend.models.*;
import com.example.ebankingbackend.repository.AccountOperationRepository;
import com.example.ebankingbackend.repository.BankAccountRepository;
import com.example.ebankingbackend.repository.CustomerRepository;
import com.example.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

   private CustomerRepository customerRepository;

   private BankAccountRepository bankAccountRepository;

    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDto) {
         Customer newCustomer= dtoMapper.fromDtoToCustomer(customerDto);
         Customer savedCustomer = customerRepository.save(newCustomer);
         return dtoMapper.fromCustomerToDto(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDto) {
        Customer newCustomer= dtoMapper.fromDtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(newCustomer);
        return dtoMapper.fromCustomerToDto(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) {
       customerRepository.deleteById(customerId);
    }
    @Override
    public CurrentBankAccountDto createCURBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
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
        return dtoMapper.fromCurrentAccountToDto(savedAccount);
    }

    @Override
    public SavingBankAccountDto createSAVBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
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
        return dtoMapper.fromSavingAccountToDto(savedAccount);
    }



    @Override
    public List<CustomerDTO> customers() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customersDto=customers.stream()
                .map((cust)->dtoMapper.fromCustomerToDto(cust))
                .collect(Collectors.toList());

        return customersDto;
    }

    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bnAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("BankAccountNotFound"));

        if(bnAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bnAccount;
            return dtoMapper.fromSavingAccountToDto(savingAccount);
        }
        if(bnAccount instanceof CurrentAccount){
            CurrentAccount currentAccount=(CurrentAccount) bnAccount;
            return dtoMapper.fromCurrentAccountToDto(currentAccount);
        }
        return null;
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

    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
         Customer customer=customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("customer not found "));
        return dtoMapper.fromCustomerToDto(customer);
    }

}
