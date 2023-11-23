package br.com.cdbservice.model.dto;

import br.com.cdbservice.model.enumerated.MovementType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCustomerUpdateDTO {
    private Long customerId;
    private BigDecimal amount;
    private MovementType movementType;
}
