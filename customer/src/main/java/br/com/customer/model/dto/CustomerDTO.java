package br.com.customer.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "O campo nome precisa ser preenchido.")
    private String name;

    @NotBlank(message = "O campo documento precisa ser preenchido.")
    private String document;

    @NotBlank(message = "O campo email precisa ser preenchido.")
    private String email;
}
