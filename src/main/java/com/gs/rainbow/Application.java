package com.gs.rainbow;

import static reactor.bus.selector.Selectors.$;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.gs.rainbow.cep.ComplexEvent;
import com.gs.rainbow.cep.EventProcessor;
import com.gs.rainbow.cep.EventWrap;
import com.gs.rainbow.cep.events.CustomerEventHandler;
import com.gs.rainbow.cep.reactors.Publisher;
import com.gs.rainbow.cep.reactors.Receiver;
import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.messaging.rabbitmq.RabbitMQReceiver;
import com.gs.rainbow.persistence.repositories.CustomerRepository;

import reactor.Environment;
import reactor.bus.EventBus;



@ComponentScan ({
	"com.gs.rainbow.persistence.events",
	"com.gs.rainbow.cep.events",
	"com.gs.rainbow.cep.reactors",
	"com.gs.rainbow.cep"
})
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	final static String queueName = "rainbow";
	
	@Bean
	Environment env() {
		return Environment.initializeIfEmpty().assignErrorJournal();
		
	}
	
	@Bean
	EventBus createEventBus(Environment env) {
		return EventBus.create(env, Environment.THREAD_POOL);
	}

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Receiver receiver;
	
	@Autowired
	private Publisher publisher;
	
	@Autowired
	private EventProcessor eventProcessor;
	
	@Bean
	public CountDownLatch latch() {
		return new CountDownLatch(1);
	}
	
	@Override
	public void run(String... args) throws Exception {
		eventBus.on($("customers"), receiver);
		
		
	}
	
	@Bean CustomerEventHandler customerEventHandler() {
	    return new CustomerEventHandler();
	}

////////////////////////////////////////////////
	//@Autowired
	//AnnotationConfigApplicationContext context;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("rainbow-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, 
		MessageListenerAdapter listenerAdapter) {

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		
		
		
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;

	}

	@Bean
	RabbitMQReceiver rabbitMQReceiver() {
		return new RabbitMQReceiver();
	}
/*	
	@Bean
	public DefaultClassMapper typeMapper() {
		DefaultClassMapper typeMapper = new DefaultClassMapper();
		
		HashMap<String, Class> idClassMapping = new HashMap<String, Class>();
		idClassMapping.put("customer", Customer.class);
		typeMapper.setIdClassMapping(idClassMapping);
		
		
		return typeMapper;
	}
	
	@Bean
	public MessageConverter messageConverter() {
		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
		jsonMessageConverter.setClassMapper(typeMapper);
		return jsonMessageConverter;
	}
*/
	@Bean
	MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			
			EventWrap eventWrap = new EventWrap<Customer>(
				new Customer("Gilber", "Kahn"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);
			
			eventWrap = new EventWrap<Customer>(
				new Customer("Jack", "Bauer"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);
				
			eventWrap = new EventWrap<Customer>(
				new Customer("Michelle", "Dessler"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);
					
					
			eventWrap = new EventWrap<Customer>(
				new Customer("David", "Palmer"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);
						
			eventWrap = new EventWrap<Customer>(
				new Customer("Kim", "Bauer"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);

			eventWrap = new EventWrap<Customer>(
				new Customer("Chloe", "O'Brian"), ComplexEvent.MISC);
			publisher.publishCustomers(eventWrap);




			log.info("Customers found with findAll():");
			log.info("-------------------------------");

			for(Customer customer: repository.findAll()) {
				log.info(customer.toString());
			}

			log.info("");


			Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			if(null != customer)
				log.info(customer.toString());
			log.info("");


			log.info("Customer found with findByLastName('Bauyer'):");
			log.info("---------------------------------------------");

			for (Customer bauer: repository.findByLastName("Bauer")) {
				if(null != bauer)
					log.info(bauer.toString());
			}

			log.info("");



			rabbitTemplate.convertAndSend(queueName, new Customer("Gowri", "Shankar"));
			rabbitMQReceiver().getLatch().await(10000, TimeUnit.MILLISECONDS);
			
			//context.close();


		};
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext appContext = SpringApplication.run(Application.class, args);
		appContext.getBean(Environment.class).shutdown();
		
		
		
	}
	
}

/***********REFERENCES**********/
// https://www.safaribooksonline.com/blog/2013/09/30/rest-hypermedia/
// https://springframework.guru/using-h2-and-oracle-with-spring-boot/#comment-415


