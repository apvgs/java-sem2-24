package br.com.fiap.dao;

public final class EnderecoDaoFactory {
    private EnderecoDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static EnderecoDao getEnderecoDao() {
        return new EnderecoDaoImpl();
    }
}
