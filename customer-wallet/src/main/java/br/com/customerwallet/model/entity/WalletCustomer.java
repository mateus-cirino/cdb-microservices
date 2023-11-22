package br.com.customerwallet.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class WalletCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private BigDecimal balance;

    @Column
    private Long customerId;

    public WalletCustomer toDTO() {
        final WalletCustomer walletCustomer = new WalletCustomer();
        walletCustomer.setId(this.id);
        walletCustomer.setBalance(this.balance);
        walletCustomer.setCustomerId(this.customerId);

        return walletCustomer;
    }
}
