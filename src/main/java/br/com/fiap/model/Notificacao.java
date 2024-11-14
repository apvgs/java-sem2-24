package br.com.fiap.model;

import java.time.LocalDateTime;

public class Notificacao {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private LeituraEnergia leituraEnergia;

    public Notificacao(String mensagem, LeituraEnergia leituraEnergia) {
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.leituraEnergia = leituraEnergia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LeituraEnergia getLeituraEnergia() {
        return leituraEnergia;
    }

    public void setLeituraEnergia(LeituraEnergia leituraEnergia) {
        this.leituraEnergia = leituraEnergia;
    }
}
