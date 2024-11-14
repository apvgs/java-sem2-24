package br.com.fiap.model;

public class DispositivoMedicao {

    private Long id;
    private String nome;
    private StatusDispositvo status;
    private String localizacao;
    private Endereco endereco;

    public DispositivoMedicao(String nome, String localizacao, Endereco endereco) {
        this.nome = nome;
        this.status = StatusDispositvo.ATIVO;
        this.localizacao = localizacao;
        this.endereco = endereco;
    }

    public void ativarDispositivo() {
        this.status = StatusDispositvo.ATIVO;
    }

    public void desativarDispositivo() {
        this.status = StatusDispositvo.INATIVO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusDispositvo getStatus() {
        return status;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
