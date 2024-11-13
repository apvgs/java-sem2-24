package br.com.fiap.service;

public class LoginServiceFactory {

    private LoginServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static LoginService create() {
        return new LoginServiceImpl();
    }
}
