package com.example.ebankingbackend.dto;

import com.example.ebankingbackend.models.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class CurrentBankAccountDto extends BankAccountDto{

    private double overDraft;
}
