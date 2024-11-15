package br.com.fiap.service;

public class ConsumoDiarioServiceFactory {

    private ConsumoDiarioServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static ConsumoDiarioService create() {
        return new ConsumoDiarioSeviceImpl();
    }
}
