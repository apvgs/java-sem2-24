package br.com.fiap.service;

public class LeituraEnergiaServiceFactory {

    private LeituraEnergiaServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static LeituraEnergiaService create() {
        return new LeituraEnergiaServiceImpl();
    }
}
