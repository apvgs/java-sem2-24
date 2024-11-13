package br.com.fiap.dao;

public final class LoginDaoFactory {
    private LoginDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static LoginDao getLoginDao() {
        return new LoginDaoImpl();
    }
}
