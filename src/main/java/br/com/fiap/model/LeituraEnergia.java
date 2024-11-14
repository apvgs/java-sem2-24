package br.com.fiap.model;

import java.time.LocalDateTime;

public class LeituraEnergia {

    private Long id;
    private LocalDateTime dataMedicao;
    private Double consumo;
    private DispositivoMedicao dispositivoMedicao;

    public LeituraEnergia(Double consumo, DispositivoMedicao dispositivoMedicao) {
        this.dataMedicao = LocalDateTime.now();
        this.consumo = consumo;
        this.dispositivoMedicao = dispositivoMedicao;
    }

    public LeituraEnergia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataMedicao() {
        return dataMedicao;
    }

    public void setDataMedicao(LocalDateTime dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    public Double getConsumo() {
        return consumo;
    }

    public void setConsumo(Double consumo) {
        this.consumo = consumo;
    }

    public DispositivoMedicao getDispositivoMedicao() {
        return dispositivoMedicao;
    }

    public void setDispositivoMedicao(DispositivoMedicao dispositivoMedicao) {
        this.dispositivoMedicao = dispositivoMedicao;
    }
}
