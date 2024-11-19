package br.com.fiap.dto;

public record DispoitivoRequestDto(
        Long id,
        String localizacao,
        String usuario,
        String codigo
) {
}
