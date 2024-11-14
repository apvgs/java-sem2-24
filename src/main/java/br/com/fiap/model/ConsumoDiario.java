package br.com.fiap.model;

import java.time.LocalDate;

public class ConsumoDiario {

    private Long id;
    private Double consumoDiario;
    private LocalDate date;
    private Endereco endereco;

    public ConsumoDiario(Double consumoDiario, LocalDate date, Endereco endereco) {
        this.consumoDiario = consumoDiario;
        this.date = date;
        this.endereco = endereco;
    }

    public ConsumoDiario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getConsumoDiario() {
        return consumoDiario;
    }

    public void setConsumoDiario(Double consumoDiario) {
        this.consumoDiario = consumoDiario;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
