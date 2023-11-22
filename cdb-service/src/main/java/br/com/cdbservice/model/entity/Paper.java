package br.com.cdbservice.model.entity;

import br.com.cdbservice.model.dto.PaperDTO;
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
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "valor")
    private BigDecimal value;

    public PaperDTO toDTO() {
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setId(this.id);
        paperDTO.setValue(this.value);

        return paperDTO;
    }
}
