package br.com.fiap.model;

public class DispositivoMedicao {

    private Long id;
    private String codigo;
    private StatusDispositvo status;
    private String localizacao;
    private Usuario usuario;

    public DispositivoMedicao(String codigo, String localizacao, Usuario usuario) {
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
