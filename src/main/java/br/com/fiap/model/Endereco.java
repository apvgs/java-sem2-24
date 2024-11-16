package br.com.fiap.model;

import br.com.fiap.exception.CepInvalido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {

    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Usuario usuario;
    @JsonProperty("logradouro")
    private String rua;
    private String bairro;
    @JsonProperty("localidade")
    private String cidade;
    @JsonProperty("uf")
    private String estado;
    private String cep;
    @JsonIgnore
    private Integer numero;
    @JsonIgnore
    private String apelido;

    public Endereco() {
    }

    public Endereco(String cep, int numero, Usuario usuario, String apelido) throws CepInvalido {
        cep = verificaCep(cep);
        Endereco endereco = PegaEnderecoViaApi.buscarEndereco(cep);
        this.cep = cep;
        this.numero = numero;
        this.bairro = endereco.bairro;
        this.cidade = endereco.cidade;
        this.estado = endereco.estado;
        this.rua = endereco.rua;
        this.usuario = usuario;
        this.apelido = apelido;
    }

    private String verificaCep(String cep) throws CepInvalido {
        cep = cep.replace("-","");
        if(cep.length() != 8){
            throw new CepInvalido("Cep Invalido");
        }
        return cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
}

