package br.com.fiap.dao;

public final class LeituraEnergiaDaoFactory {

    private LeituraEnergiaDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static LeituraEnergiaDao getLeituraEnergiaDao() {
        return new LeituraEnergiaDaoImpl();
    }
}

