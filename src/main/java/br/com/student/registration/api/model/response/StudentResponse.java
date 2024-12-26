package br.com.student.registration.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {

    private String name;

    private String email;

    private String phone;

    private String cpf;

    private int age;
}
