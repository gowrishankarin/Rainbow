package com.gs.rainbow.cep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gs.rainbow.cep.events.CustomerEventHandler;
import com.gs.rainbow.domain.Customer;

@Service
public class EventProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerEventHandler.class);
	
	
	public EventProcessor() {
		System.out.println("This is Event Processor");
	}
	
	public void createCustomerEvent(Customer customer) {
		log.info("This is fired BEFORE creating Customer " + customer.getFirstName());
		
	}
	
	public void miscEvent(Customer customer) {
		log.info("This is fired BEFORE creating Customer " + customer.getFirstName());
	}
	
	
}