package br.com.student.registration.domain.filter;

public record StudentFilter(
        String name,
        String cpf,
        String email
) {
}
