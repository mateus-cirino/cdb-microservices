package br.com.cdbservice.model.dto;

import br.com.cdbservice.model.entity.WalletCDB;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletCDBDTO {
    private Long id;

    @NotNull
    private PaperDTO paper;

    @NotNull
    private Long customerId;

    @NotNull
    private BigDecimal amount;

    private BigDecimal value;

    public WalletCDB fromDTO() {
        WalletCDB walletCDB = new WalletCDB();
        walletCDB.setId(this.id);
        walletCDB.setPaper(this.paper.fromDTO());
        walletCDB.setCustomerId(this.customerId);
        walletCDB.setAmount(this.amount);

        return walletCDB;
    }
}
