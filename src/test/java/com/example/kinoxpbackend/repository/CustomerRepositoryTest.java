package com.example.kinoxpbackend.repository;

import com.example.kinoxpbackend.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john.doe@example.com", "123456789");
        customerRepository.save(testCustomer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer("Jane Doe", "jane.doe@example.com", "987654321");
        Customer savedCustomer = customerRepository.save(customer);
        assertNotNull(savedCustomer.getCustomerId());
        assertEquals("Jane Doe", savedCustomer.getName());
    }

    @Test
    void testFindCustomerById() {
        Optional<Customer> foundCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().getName());
    }

    @Test
    void testDeleteCustomer() {
        customerRepository.delete(testCustomer);
        Optional<Customer> deletedCustomer = customerRepository.findById(testCustomer.getCustomerId());
        assertFalse(deletedCustomer.isPresent());
    }
}