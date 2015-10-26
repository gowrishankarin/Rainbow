package com.gs.rainbow.cep.reactors;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.rainbow.cep.EventProcessor;
import com.gs.rainbow.cep.EventWrap;
import com.gs.rainbow.cep.events.CustomerEventHandler;
import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;

import reactor.bus.Event;
import reactor.fn.Consumer;



@Service
public class Receiver implements Consumer<Event<EventWrap<Customer>>> {

	private static final Logger log = LoggerFactory.getLogger(CustomerEventHandler.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EventProcessor eventProcessor;
	
	@Autowired
	CountDownLatch latch;

	public void accept(Event<EventWrap<Customer>> eventWrap) {
		EventWrap<Customer> newEvent  = eventWrap.getData();
		Customer customer = newEvent.getObject();
		
		switch(newEvent.getComplexEvent()) {
		
			case BEFORE_CREATE:
				eventProcessor.createCustomerEvent(customer);
				break;
		
			case AFTER_CREATE:
				log.info("This is fired AFTER creating Customer " + customer.getLastName());
				break;
		
			case BEFORE_UPDATE:
				log.info("This is fired BEFORE updating Customer " + customer.getFirstName());
				break;
		
			case AFTER_UPDATE:
				log.info("This is fired AFTER updating Customer " + customer.getLastName());
				break;
				
			case BEFORE_DELETE:
				log.info("This is fired BEFORE deleting Customer " + customer.getFirstName());
				break;
		
			case AFTER_DELETE:
				log.info("This is fired AFTER deleting Customer " + customer.getFirstName());
				break;
				
			case BEFORE_GET:
				break;
		
			case AFTER_GET:
				break;
				
			case MISC:
				customerRepository.save((Customer)newEvent.getObject());
				break;
				
			case TEST:	
			default:
				break;

		}
	//	latch.countDown();
	}
}