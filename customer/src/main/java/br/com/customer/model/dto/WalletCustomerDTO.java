package br.com.customer.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class WalletCustomerDTO {
    private Long id;

    private BigDecimal balance;

    private Long customerId;
}
