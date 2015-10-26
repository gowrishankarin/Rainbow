package com.gs.rainbow.messaging.rabbitmq;

import java.util.concurrent.CountDownLatch;

public class RabbitMQReceiver {
	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println(message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		System.out.println("Message Received");
		return latch;
	}
}