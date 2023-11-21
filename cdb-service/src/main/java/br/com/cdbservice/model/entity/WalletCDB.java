package br.com.cdbservice.model.entity;

import br.com.cdbservice.model.dto.WalletCDBDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_cdb")
@Getter
@Setter
public class WalletCDB {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Paper paper;

    @Column
    private Long customerId;

    @Column
    private BigDecimal amount;

    public WalletCDBDTO toDTO() {
        WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setId(this.id);
        walletCDBDTO.setPaper(this.paper.toDTO());
        walletCDBDTO.setCustomerId(this.customerId);
        walletCDBDTO.setAmount(this.amount);

        return walletCDBDTO;
    }
}
