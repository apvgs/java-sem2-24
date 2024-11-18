package br.com.fiap.dto;

public record NotificacaoDto(
        Long id,
        String mensagem,
        String data
) {
}
