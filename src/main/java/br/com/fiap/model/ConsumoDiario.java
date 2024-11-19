package br.com.fiap.model;

import java.time.LocalDate;

public class ConsumoDiario {

    private Long id;
    private Double consumoDiario;
    private LocalDate date;
    private Usuario usuario;

    public ConsumoDiario(Double consumoDiario, LocalDate date, Usuario usuario) {
        this.consumoDiario = consumoDiario;
        this.date = date;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
