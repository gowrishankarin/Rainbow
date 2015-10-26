package com.gs.rainbow.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gs.rainbow.domain.Customer;

public interface CustomerService{

	Iterable<Customer> listAllCustomers();
	
	Page<Customer> pageAllCustomers(Pageable pageable);

}