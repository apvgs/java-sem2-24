package br.com.fiap.dto;

public record EnderecoResponseDto(
        Long id,
        Long usuarioId,
        String rua,
        String cep,
        Integer numero,
        String cidade,
        String estado
) {
}
