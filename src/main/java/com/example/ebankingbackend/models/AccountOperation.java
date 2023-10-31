package com.example.ebankingbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.Operation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountOperation  implements Serializable {
    @Id
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;

}
