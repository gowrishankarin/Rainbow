package com.gs.rainbow.services;

import com.gs.rainbow.domain.Customer;

public interface CustomerService {

	Iterable<Customer> listAllCustomers();

}