package com.gs.rainbow.cep.reactors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.rainbow.domain.Customer;

import reactor.bus.Event;
import reactor.bus.EventBus;


@Service
public class Publisher {

	@Autowired
	EventBus eventBus;

	//@Autowired
	//CountDownLatch latch; 
	
	public void publishCustomers(Customer newCustomer) throws InterruptedException {

		eventBus.notify("customers", Event.wrap(newCustomer));

	//	latch.await();

	}
}