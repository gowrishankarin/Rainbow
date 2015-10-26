package com.gs.rainbow.cep.reactors;


import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.rainbow.cep.EventWrap;
import com.gs.rainbow.domain.Customer;

import reactor.bus.Event;
import reactor.bus.EventBus;



@Service
public class Publisher {

	@Autowired
	EventBus eventBus;
	
	@Autowired
	CountDownLatch latch;

	public void publishCustomers(EventWrap<Customer> newEvent) throws InterruptedException {
		// EXCEPTION HERE
		eventBus.notify("customers", Event.wrap(newEvent));
		//latch.await();
		
		

	}

}