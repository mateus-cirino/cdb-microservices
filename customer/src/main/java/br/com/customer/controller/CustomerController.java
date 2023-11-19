package br.com.customer.controller;

import br.com.customer.exception.CustomerSaveException;
import br.com.customer.model.dto.CustomerDTO;
import br.com.customer.model.entity.Customer;
import br.com.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;
    @PostMapping(value = "/save")
    public ResponseEntity<Customer> save(@Valid @RequestBody CustomerDTO customerDTO) {
        LOGGER.info("Iniciando o processo de persistência do customer.");
        LOGGER.info(String.format("Dados que foram recebidos via @RequestBody: %s", customerDTO.toString()));

        final Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setDocument(customerDTO.getDocument());
        customer.setEmail(customerDTO.getEmail());

        LOGGER.info("Criação da entidade customer concluída com sucesso.");

        final ResponseEntity responseEntity;

        try {
            LOGGER.info("Tentando persistir o customer.");
            final Customer newCustomer = customerService.save(customer);
            LOGGER.info(String.format("Persistência concluída com sucesso. Id do customer: %s.", newCustomer.getId()));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(customer);
        } catch (final Exception e) {
            LOGGER.error(String.format("Não foi possível persistir o customer, mensagem de erro: %s. Classe: CustomerController, método: save.", e.getMessage()));
            throw new CustomerSaveException(e.getMessage());
        }

        return responseEntity;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll());
    }
}
