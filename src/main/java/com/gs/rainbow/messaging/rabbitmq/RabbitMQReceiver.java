package com.gs.rainbow.messaging.rabbitmq;

import java.util.concurrent.CountDownLatch;

import com.gs.rainbow.domain.Customer;

public class RabbitMQReceiver {
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(Customer customer) {
		System.out.println(customer.toString());
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		System.out.println("Message Received");
		return latch;
	}
}