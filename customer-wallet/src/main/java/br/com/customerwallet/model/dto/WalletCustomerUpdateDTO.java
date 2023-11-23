package br.com.customerwallet.model.dto;

import br.com.customerwallet.model.enumerated.MovementType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCustomerUpdateDTO {
    private Long customerId;
    private BigDecimal amount;
    private MovementType movementType;

    public BigDecimal getAmount() {
        return movementType == MovementType.BUY ? amount.negate() : amount;
    }
}
