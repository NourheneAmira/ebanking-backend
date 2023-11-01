package com.example.ebankingbackend.mapers;

import com.example.ebankingbackend.dto.BankAccountDto;
import com.example.ebankingbackend.dto.CurrentBankAccountDto;
import com.example.ebankingbackend.dto.CustomerDTO;
import com.example.ebankingbackend.dto.SavingBankAccountDto;
import com.example.ebankingbackend.models.BankAccount;
import com.example.ebankingbackend.models.CurrentAccount;
import com.example.ebankingbackend.models.Customer;
import com.example.ebankingbackend.models.SavingAccount;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomerToDto (Customer customer){
        CustomerDTO customerDTO= new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromDtoToCustomer(CustomerDTO customerDTO){
        Customer customer= new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public BankAccount fromDtoToBankAccount(BankAccountDto bankAccountDto){
        BankAccount bankAccount=new BankAccount();
        BeanUtils.copyProperties(bankAccountDto,bankAccount);
        return bankAccount;
    }
    public BankAccountDto fromBankAccountToDto(BankAccount bankAccount){
        BankAccountDto bankAccountDto= new BankAccountDto();
        BeanUtils.copyProperties(bankAccount,bankAccountDto);
        return bankAccountDto;
    }
    public SavingAccount fromDtoToSavingAccount( SavingBankAccountDto savingBankAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
        return savingAccount;
    }
    public SavingBankAccountDto fromSavingAccountToDto( SavingAccount savingAccount){
        SavingBankAccountDto savingAccountDto=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        return savingAccountDto;
    }
    public CurrentBankAccountDto fromCurrentAccountToDto(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto= new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
        return currentBankAccountDto;
    }
    public CurrentAccount fromDtoToCurrentAccount( CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        return currentAccount;
    }

}
