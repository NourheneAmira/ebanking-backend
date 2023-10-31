package com.example.ebankingbackend.repository;
import com.example.ebankingbackend.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
