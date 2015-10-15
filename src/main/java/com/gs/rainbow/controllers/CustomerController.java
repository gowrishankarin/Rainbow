package com.gs.rainbow.controllers;

import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(value="/customers", method=RequestMethod.GET)
	public @ResponseBody Iterable<Customer> list(Model model) {
		model.addAttribute("customers", customerService.listAllCustomers());
		return customerService.listAllCustomers();
	}

}	