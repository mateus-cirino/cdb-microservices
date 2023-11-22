package br.com.cdbservice.model.dto;

import br.com.cdbservice.model.entity.Paper;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaperDTO {
    private Long id;

    @NotNull(message = "O campo valor precisa ser preenchido.")
    private BigDecimal value;

    public Paper fromDTO() {
        final Paper paper = new Paper();
        paper.setId(this.id);
        paper.setValue(this.value);

        return paper;
    }
}
