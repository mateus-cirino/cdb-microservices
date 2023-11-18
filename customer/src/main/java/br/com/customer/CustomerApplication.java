package br.com.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(CustomerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Inicializando o micro serviço customer.");
		SpringApplication.run(CustomerApplication.class, args);
		LOGGER.info("Micro serviço customer inicializado com sucesso.");
	}

}
