package com.example.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends  RuntimeException{
    public BankAccountNotFoundException (String message){
        super(message);
    }
}
