package br.com.customer.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
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

        log.info("Parse da entidade customer para customerDTO concluída com sucesso.");

        final CustomerDTO newCustomerDTO = customerService.save(customer).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", newCustomerDTO.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(newCustomerDTO);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<CustomerDTO> findById(@RequestParam final Long id) {
        log.info(String.format("Iniciando o processo de busca do customer com o id %s.", id));

        final CustomerDTO customerFound = customerService.findById(id).toDTO();

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", customerFound.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(customerFound);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        log.info("Iniciando o processo de busca de todos os customers.");

        final List<Customer> customers = customerService.findAll();

        final List<CustomerDTO> customersDTO = customers.stream().map(Customer::toDTO).collect(Collectors.toList());

        log.info(String.format("Dados que estão sendo devolvidos para a requisição: %s", customersDTO.stream().map(CustomerDTO::toString).collect(Collectors.toList())));

        return ResponseEntity.status(HttpStatus.OK).body(customersDTO);
    }
}
