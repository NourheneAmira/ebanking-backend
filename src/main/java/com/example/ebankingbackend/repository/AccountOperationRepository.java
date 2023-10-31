package com.example.ebankingbackend.repository;

import com.example.ebankingbackend.models.AccountOperation;
import com.example.ebankingbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository <AccountOperation, Long> {
}
