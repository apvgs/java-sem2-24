package br.com.fiap.model;

import br.com.fiap.exception.ErroAoCriarLogin;

public class Login {

    private Long id;
    private String email;
    private String senha;

    public Login() {}

    public Login(String email, String senha) throws ErroAoCriarLogin {
        setEmail(email);
        setSenha(senha);
    }


    public void setSenha(String senha) throws ErroAoCriarLogin {
        if (senha.length() < 6) {
            throw new ErroAoCriarLogin("Senha deve ser maior que 6 caracteres");
        }
        this.senha = senha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) throws ErroAoCriarLogin {
        if(email.isBlank() || !(email.contains("@") && email.contains(".com"))){
            throw new ErroAoCriarLogin("Email invalido");
        }
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
