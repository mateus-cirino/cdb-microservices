package br.com.customer.controller;

import br.com.customer.exception.CustomerSaveException;
import br.com.customer.model.dto.CustomerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @Test
    void saveCustomerSuccess() {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Mateus");
        customerDTO.setDocument("Identidade");
        customerDTO.setEmail("mateus@gmail.com");

        final CustomerDTO customerSaved = customerController.save(customerDTO).getBody();

        Assertions.assertNotNull(customerSaved.getId());
    }

    @Test
    void saveCustomerNameRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setDocument("Identidade");
            customerDTO.setEmail("mateus@gmail.com");

            customerController.save(customerDTO);
        });
    }

    @Test
    void saveCustomerDocumentRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName("Mateus");
            customerDTO.setEmail("mateus@gmail.com");

            customerController.save(customerDTO);
        });
    }

    @Test
    void saveCustomerEmailRequiredFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName("Mateus");
            customerDTO.setEmail("mateus@gmail.com");

            customerController.save(customerDTO);
        });
    }

    @Test
    void saveCustomerDocumentUniqueFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName("Mateus");
            customerDTO.setDocument("identidade1");
            customerDTO.setEmail("mateus@gmail.com1");

            customerController.save(customerDTO);

            final CustomerDTO customerDTODocumentDuplicated = new CustomerDTO();
            customerDTODocumentDuplicated.setName("Mateus");
            customerDTODocumentDuplicated.setDocument("identidade1");
            customerDTODocumentDuplicated.setEmail("mateus@gmail.com2");

            customerController.save(customerDTODocumentDuplicated);
        });
    }

    @Test
    void saveCustomerEmailUniqueFailure() {
        Assertions.assertThrowsExactly(CustomerSaveException.class, () -> {
            final CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setName("Mateus");
            customerDTO.setDocument("identidade1");
            customerDTO.setEmail("mateus@gmail.com1");

            customerController.save(customerDTO);

            final CustomerDTO customerDTOEmailDuplicated = new CustomerDTO();
            customerDTOEmailDuplicated.setName("Mateus");
            customerDTOEmailDuplicated.setDocument("identidade2");
            customerDTOEmailDuplicated.setEmail("mateus@gmail.com1");

            customerController.save(customerDTOEmailDuplicated);
        });
    }

    @Test
    void findAllEmptyListSucess() {
        Assertions.assertEquals(Collections.emptyList(), customerController.findAll().getBody());
    }

    @Test
    void findAllWithElementListSucess() {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Mateus");
        customerDTO.setDocument("Identidade");
        customerDTO.setEmail("mateus@gmail.com");

        final CustomerDTO customerSaved = customerController.save(customerDTO).getBody();

        Assertions.assertNotNull(customerSaved.getId());

        Assertions.assertNotEquals(Collections.emptyList(), customerController.findAll().getBody());
    }
}
