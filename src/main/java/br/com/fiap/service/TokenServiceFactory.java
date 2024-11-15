package br.com.fiap.service;

public class TokenServiceFactory {

    private TokenServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static TokenService create() {
        return new TokenServiceImpl();
    }
}
