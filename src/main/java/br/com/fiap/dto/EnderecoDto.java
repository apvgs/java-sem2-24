package br.com.fiap.dto;

public record EnderecoDto(
        String cep,
        Integer numero,
        String apelido
) {
}
