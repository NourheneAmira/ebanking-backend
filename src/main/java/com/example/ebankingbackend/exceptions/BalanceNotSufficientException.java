package com.example.ebankingbackend.exceptions;

public class BalanceNotSufficientException extends RuntimeException{
    public BalanceNotSufficientException(String message){super(message);}
}
