package br.com.cdbservice.model.entity;

import br.com.cdbservice.model.dto.WalletCDBDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

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

    @Transient
    public BigDecimal getValue() {
        return Optional.ofNullable(amount).orElse(BigDecimal.ZERO).multiply(Optional.ofNullable(paper).map(Paper::getValue).orElse(BigDecimal.ZERO));
    }

    public WalletCDBDTO toDTO() {
        WalletCDBDTO walletCDBDTO = new WalletCDBDTO();
        walletCDBDTO.setId(this.id);
        walletCDBDTO.setPaper(this.paper.toDTO());
        walletCDBDTO.setCustomerId(this.customerId);
        walletCDBDTO.setAmount(this.amount);
        walletCDBDTO.setValue(this.getValue());

        return walletCDBDTO;
    }
}
