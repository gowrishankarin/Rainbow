package com.gs.rainbow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.gs.rainbow.domain.Customer;
import com.gs.rainbow.persistence.repositories.CustomerRepository;


@ComponentScan ({
	"com.gs.rainbow.persistence.events"
})

@EnableAutoConfiguration 
@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	


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
}