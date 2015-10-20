package com.gs.rainbow.cep.events;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.gs.rainbow.cep.ComplexEvent;
import com.gs.rainbow.cep.EventWrap;
import com.gs.rainbow.cep.reactors.Publisher;
import com.gs.rainbow.domain.Customer;

@Component
@RepositoryEventHandler(Customer.class)
public class CustomerEventHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomerEventHandler.class);
	
	@Autowired
	Publisher publisher;


	@HandleBeforeCreate
	public void handleBeforeCreate(Customer customer) {
		// System.out.println("This is fired before creating Customer");

	}

	@HandleAfterCreate
	public void handleAfterCreate(Customer customer) {
		log.info("This is fired AFTER creating Customer " + customer.getLastName());
		
		EventWrap<Customer> customerEvent = new EventWrap<Customer>(
			customer, ComplexEvent.MISC);
		
		customer.setCreationTime(new Date());
		
		
		try {
			publisher.publishCustomers(customerEvent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@HandleBeforeSave // Update
	public void handleBeforeSave(Customer customer) {
		log.info("This is fired BEFORE updating Customer " + customer.getFirstName());
	}

	@HandleAfterSave // Update
	public void handleAfterSave(Customer customer) {
		log.info("This is fired AFTER updating Customer " + customer.getLastName());
	}

	@HandleAfterDelete
	public void handleAfterDelete(Customer customer) {
		log.info("This is fired AFTER deleting Customer " + customer.getFirstName());
	}
}