package com.gs.rainbow.services;

import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {


	private CustomerRepository customerRepository;


	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Iterable<Customer> listAllCustomers() {
		return customerRepository.findAll();
	}



}