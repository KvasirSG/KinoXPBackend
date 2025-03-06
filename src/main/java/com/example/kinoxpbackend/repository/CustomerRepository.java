package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
