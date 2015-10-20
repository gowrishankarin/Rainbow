package com.gs.rainbow;

import static reactor.bus.selector.Selectors.$;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.gs.rainbow.cep.reactors.Publisher;
import com.gs.rainbow.cep.reactors.Receiver;
import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;

import reactor.Environment;
import reactor.bus.EventBus;

@Configuration
@ComponentScan ({
	"com.gs.rainbow.persistence.events",
	"com.gs.rainbow.cep.reactors"
})
@EnableAutoConfiguration 
public class Application implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			log.info("Customers found with findAll():");
			log.info("-------------------------------");

			for(Customer customer: repository.findAll()) {
				log.info(customer.toString());
			}

			log.info("");


			Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");


			log.info("Customer found with findByLastName('Bauyer'):");
			log.info("---------------------------------------------");

			for (Customer bauer: repository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}

			log.info("");
		};
	}
	
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
	
	@Override
	public void run(String... args) throws Exception {
		eventBus.on($("customers"), receiver);
		publisher.publishCustomers(new Customer("Gowri", "Shankar"));
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ApplicationContext appContext = SpringApplication.run(Application.class, args);
		appContext.getBean(Environment.class).shutdown();
		
	}
	
}

/***********REFERENCES**********/
// https://www.safaribooksonline.com/blog/2013/09/30/rest-hypermedia/
// https://springframework.guru/using-h2-and-oracle-with-spring-boot/#comment-415


