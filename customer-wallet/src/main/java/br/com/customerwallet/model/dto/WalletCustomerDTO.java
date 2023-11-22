package br.com.customerwallet.model.dto;

import br.com.customerwallet.model.entity.WalletCustomer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCustomerDTO {
    private Long id;

    @NotNull(message = "O campo saldo precisa ser preenchido.")
    private BigDecimal balance;

    @NotNull(message = "O campo customer precisa ser preenchido.")
    private Long customerId;

    public WalletCustomer fromDTO() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setId(this.id);
        walletCustomer.setBalance(this.balance);
        walletCustomer.setCustomerId(this.customerId);

        return walletCustomer;
    }
}
