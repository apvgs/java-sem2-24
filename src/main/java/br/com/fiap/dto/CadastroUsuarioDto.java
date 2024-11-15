package br.com.fiap.dto;

public record CadastroUsuarioDto(
        String email,
        String senha,
        String cpf,
        String nome
) {
}
