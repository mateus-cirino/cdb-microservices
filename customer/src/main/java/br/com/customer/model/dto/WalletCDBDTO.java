package br.com.customer.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCDBDTO {
    private Long id;

    private PaperDTO paper;

    private Long customerId;

    private BigDecimal amount;

    private BigDecimal value;
}
