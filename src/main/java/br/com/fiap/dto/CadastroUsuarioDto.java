package br.com.fiap.dto;

public record CadastroUsuarioDto(
        String email,
        String password,
        String cpf,
        String nome
) {
}
