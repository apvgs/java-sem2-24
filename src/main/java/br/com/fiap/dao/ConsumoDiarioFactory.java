package br.com.fiap.dao;

public final class ConsumoDiarioFactory {

    private ConsumoDiarioFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static ConsumoDiarioDao getConsumoDiarioDao() {
        return new ConsumoDiarioImpl();
    }
}

