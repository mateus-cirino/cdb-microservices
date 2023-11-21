package br.com.customer.controller;

import br.com.customer.exception.CustomerSaveException;
import br.com.customer.model.dto.CustomerDTO;
import br.com.customer.model.entity.Customer;
import br.com.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @PostMapping(value = "/save")
    public ResponseEntity<CustomerDTO> save(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Iniciando o processo de persistência do customer.");
        log.info(String.format("Dados que foram recebidos via @RequestBody: %s", customerDTO.toString()));

        final Customer customer = customerDTO.fromDTO();

        log.info("Criação da entidade customer concluída com sucesso.");

        final ResponseEntity responseEntity;

        try {
            log.info("Tentando persistir o customer.");
            final Customer newCustomer = customerService.save(customer);
            log.info(String.format("Persistência concluída com sucesso. Id do customer: %s.", newCustomer.getId()));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(customer.toDTO());
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o customer, mensagem de erro: %s. Classe: CustomerController, método: save.", e.getMessage()));
            throw new CustomerSaveException(e.getMessage());
        }

        return responseEntity;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os customers.");

        final List<Customer> customers = customerService.findAll();

        log.info("Busca de todos os customers foi concluída com sucesso.");

        final List<CustomerDTO> customersDTO = customers.stream().map(Customer::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados encontrados: %s", customersDTO.stream().map(CustomerDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(customersDTO);
    }
}
