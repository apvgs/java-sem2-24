package br.com.fiap.dao;

public final class ConsumoDiarioDaoFactory {

    private ConsumoDiarioDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static ConsumoDiarioDao getConsumoDiarioDao() {
        return new ConsumoDiarioDaoImpl();
    }
}

