package com.example.boardcamp.services;

import com.example.boardcamp.dtos.CustomerDTO;
import com.example.boardcamp.exceptions.CustomerConflictException;
import com.example.boardcamp.exceptions.CustomerNotFoundException;
import com.example.boardcamp.models.Customer;
import com.example.boardcamp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado", HttpStatus.NOT_FOUND));
    }

    public Customer createCustomer(CustomerDTO customerDTO) {

        if (customerRepository.existsByCpf(customerDTO.cpf())) {
            throw new CustomerConflictException("Cliente com esse CPF já existe", HttpStatus.CONFLICT);
        }

        Customer customer = convertToEntity(customerDTO);
        return customerRepository.save(customer);
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        return new Customer(null, customerDTO.name(), customerDTO.cpf());
    }

}
