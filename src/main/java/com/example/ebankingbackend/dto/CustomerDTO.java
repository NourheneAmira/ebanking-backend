package com.example.ebankingbackend.dto;

import com.example.ebankingbackend.models.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

}
