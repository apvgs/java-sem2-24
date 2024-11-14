package br.com.fiap.dao;

public final class UsuarioDaoFactory {

    private UsuarioDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static UsuarioDao getUsuarioDao() {
        return new UsuarioDaoImpl();
    }
}
