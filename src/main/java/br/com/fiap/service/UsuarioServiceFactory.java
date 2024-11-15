package br.com.fiap.service;

public class UsuarioServiceFactory {

    private UsuarioServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static UsuarioService create() {
        return new UsuarioServiceImpl();
    }
}
