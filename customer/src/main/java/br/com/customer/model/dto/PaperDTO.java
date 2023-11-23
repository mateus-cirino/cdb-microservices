package br.com.customer.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaperDTO {
    private Long id;

    private BigDecimal value;
}
