package br.com.customer.service;

import br.com.customer.exception.CustomerNotFoundException;
import br.com.customer.exception.CustomerSaveException;
import br.com.customer.model.entity.Customer;
import br.com.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(final Customer customer) {
        try {
            log.info("Tentando persistir o customer.");
            final Customer newCustomer = customerRepository.save(customer);
            customer.setId(newCustomer.getId());
            log.info(String.format("Persistência concluída com sucesso. Id do customer: %s.", newCustomer.getId()));
        } catch (final Exception e) {
            log.error(String.format("Não foi possível persistir o customer, mensagem de erro: %s. Classe: CustomerController, método: save.", e.getMessage()));
            throw new CustomerSaveException(e.getMessage());
        }

        return customer;
    }

    public Customer findById(final Long id) {
        log.info(String.format("Tentando buscar o customer de id %s.", id));

        final Optional<Customer> optCustomer = customerRepository.findById(id);

        final Customer customerFound;

        if (optCustomer.isPresent()) {
            log.info(String.format("Customer de id %s encontrado com sucesso.", id));

            customerFound = optCustomer.get();
        } else {
            throw new CustomerNotFoundException(String.format("O customer de id %s não existe", id));
        }

        return customerFound;
    }

    public List<Customer> findAll() {
        log.info("Tentando buscar todos os customers");

        final List<Customer> customersFound = customerRepository.findAll();

        log.info("Busca de todos os customers foi concluída com sucesso.");

        return customersFound;
    }
}
