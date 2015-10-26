package com.gs.rainbow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;
import com.gs.rainbow.services.CustomerService;

@RestController
public class CustomerController {

	private CustomerService customerService;
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
/*
	@RequestMapping(value="/customers", method=RequestMethod.GET)
	public @ResponseBody Iterable<Customer> list(Model model) {
		model.addAttribute("customers", customerService.listAllCustomers());
		return customerService.listAllCustomers();
	}*/
	
	


}	