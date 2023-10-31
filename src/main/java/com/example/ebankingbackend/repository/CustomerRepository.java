package com.example.ebankingbackend.repository;

import com.example.ebankingbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
