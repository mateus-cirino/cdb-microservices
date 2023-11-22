package br.com.customer.model.entity;


import br.com.customer.model.dto.CustomerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String document;

    @Column
    private String email;

    public CustomerDTO toDTO() {
        final CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(this.id);
        customerDTO.setName(this.name);
        customerDTO.setDocument(this.document);
        customerDTO.setEmail(this.email);

        return customerDTO;
    }
}
