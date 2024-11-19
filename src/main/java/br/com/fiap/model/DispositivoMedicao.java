package br.com.fiap.model;

public class DispositivoMedicao {

    private Long id;
    private String nome;
    private StatusDispositvo status;
    private String localizacao;
    private Usuario usuario;

    public DispositivoMedicao(String nome, String localizacao, Usuario usuario) {
        this.nome = nome;
        this.status = StatusDispositvo.ATIVO;
        this.localizacao = localizacao;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
