package br.com.fiap.dto;

import java.util.List;

public record ConsumoMesDto(
        List<ConsumoDiarioDto> consumoDiario,
        Double consumoTotal
) {
}
