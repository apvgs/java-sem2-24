package br.com.fiap.dao;

public final class NotificacaoDaoFactory {

    private NotificacaoDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static NotificacaoDao getNotificacaoDao() {
        return new NotificacaoDaoImpl();
    }
}

