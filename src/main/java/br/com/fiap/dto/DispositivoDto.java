package br.com.fiap.dto;

public record DispositivoDto(
        String nome,
        String localizacao,
        Long enderecoId
) {
}
