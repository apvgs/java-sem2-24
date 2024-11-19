package br.com.fiap.dto;

import java.util.List;

public record DashBoardDto(
        String nome,
        Long id,
        String cpf,
        String email,
        String senha,
        String data,
        Double consumoDiario,
        Double consumoMensal,
        Double mediaDiaria,
        List<LeituraEnergiaResponse> list,
        boolean existeDispositivo
) {
}
