package br.com.student.registration.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class StudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private String phone;

    @CPF
    private String cpf;

    @Positive
    private int age;
}
