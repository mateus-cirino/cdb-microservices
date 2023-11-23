package br.com.customerwallet.model.entity;

import br.com.customerwallet.model.dto.WalletCustomerDTO;
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

    public WalletCustomerDTO toDTO() {
        final WalletCustomerDTO walletCustomerDTO = new WalletCustomerDTO();
        walletCustomerDTO.setId(this.id);
        walletCustomerDTO.setBalance(this.balance);
        walletCustomerDTO.setCustomerId(this.customerId);

        return walletCustomerDTO;
    }
}
