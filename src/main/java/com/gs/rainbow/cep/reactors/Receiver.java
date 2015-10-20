package com.gs.rainbow.cep.reactors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;

import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class Receiver implements Consumer<Event<Customer>> {

	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	CustomerRepository customerRepository;

	public void accept(Event<Customer> event) {
		Customer newCustomer  = event.getData();
		customerRepository.save(newCustomer);
	}



}