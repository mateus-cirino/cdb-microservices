package br.com.customer.service;

import br.com.customer.exception.CustomerNotFoundException;
import br.com.customer.exception.CustomerSaveException;
import br.com.customer.model.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void saveCustomerSuccess() {
        final Customer customer = new Customer();
        customer.setName("Mateus");
        customer.setDocument("Identidade");
        customer.setEmail("mateus@gmail.com");

        final Customer customerSaved = customerService.save(customer);

        Assertions.assertNotNull(customerSaved.getId());
    }

    @Test
    void saveCustomerNameRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final Customer customer = new Customer();
            customer.setDocument("Identidade");
            customer.setEmail("mateus@gmail.com");

            customerService.save(customer);
        });
    }

    @Test
    void saveCustomerDocumentRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final Customer customer = new Customer();
            customer.setName("Mateus");
            customer.setEmail("mateus@gmail.com");

            customerService.save(customer);
        });
    }

    @Test
    void saveCustomerEmailRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final Customer customer = new Customer();
            customer.setName("Mateus");
            customer.setEmail("mateus@gmail.com");

            customerService.save(customer);
        });
    }

    @Test
    void saveCustomerDocumentUniqueFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final Customer customer = new Customer();
            customer.setName("Mateus");
            customer.setDocument("identidade1");
            customer.setEmail("mateus@gmail.com1");

            customerService.save(customer);

            final Customer customerDocumentDuplicated = new Customer();
            customerDocumentDuplicated.setName("Mateus");
            customerDocumentDuplicated.setDocument("identidade1");
            customerDocumentDuplicated.setEmail("mateus@gmail.com2");

            customerService.save(customerDocumentDuplicated);
        });
    }

    @Test
    void saveCustomerEmailUniqueFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final Customer customer = new Customer();
            customer.setName("Mateus");
            customer.setDocument("identidade1");
            customer.setEmail("mateus@gmail.com1");

            customerService.save(customer);

            final Customer customerEmailDuplicated = new Customer();
            customerEmailDuplicated.setName("Mateus");
            customerEmailDuplicated.setDocument("identidade2");
            customerEmailDuplicated.setEmail("mateus@gmail.com1");

            customerService.save(customerEmailDuplicated);
        });
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertEquals(Collections.emptyList(), customerService.findAll());
    }

    @Test
    void findAllWithElementListSucess() {
        final Customer customer = new Customer();
        customer.setName("Mateus");
        customer.setDocument("Identidade");
        customer.setEmail("mateus@gmail.com");

        final Customer customerSaved = customerService.save(customer);

        Assertions.assertNotNull(customerSaved.getId());

        Assertions.assertNotEquals(Collections.emptyList(), customerService.findAll());
    }

    @Test
    void findByIdCustomerThatExistsSucess() {
        final Customer customer = new Customer();
        customer.setName("Mateus");
        customer.setDocument("Identidade");
        customer.setEmail("mateus@gmail.com");

        final Customer customerSaved = customerService.save(customer);

        Assertions.assertEquals(customerService.findById(customerSaved.getId()).getId(), customerSaved.getId());
    }

    @Test
    void findByIdCustomerThatNotExistsFailure() {
        Assertions.assertThrowsExactly(CustomerNotFoundException.class, () -> {
            customerService.findById(1L);
        });
    }
}
