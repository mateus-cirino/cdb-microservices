package br.com.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		log.info("Inicializando o micro serviço customer.");
		SpringApplication.run(CustomerApplication.class, args);
		log.info("Micro serviço customer inicializado com sucesso.");
	}

}
