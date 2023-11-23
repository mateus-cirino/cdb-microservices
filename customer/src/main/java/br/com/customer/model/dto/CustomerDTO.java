package br.com.customer.model.dto;

import br.com.customer.model.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "O campo nome precisa ser preenchido.")
    private String name;

    @NotBlank(message = "O campo documento precisa ser preenchido.")
    @ToString.Exclude
    private String document;

    @NotBlank(message = "O campo email precisa ser preenchido.")
    @ToString.Exclude
    private String email;

    private WalletCustomerDTO walletCustomerDTO;

    private List<WalletCDBDTO> walletCDBDTOList;

    public BigDecimal getFullValue() {
        return Optional.ofNullable(walletCustomerDTO).map(WalletCustomerDTO::getBalance).map(it -> it.add(Optional.ofNullable(walletCDBDTOList).orElse(Collections.emptyList()).stream().map(WalletCDBDTO::getValue).reduce(BigDecimal.ZERO, BigDecimal::add))).orElse(BigDecimal.ZERO);
    }

    public Customer fromDTO() {
        final Customer customer = new Customer();
        customer.setName(this.name);
        customer.setDocument(this.document);
        customer.setEmail(this.email);

        return customer;
    }
}
