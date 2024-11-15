package br.com.fiap.service;

public class NotificacaoServiceFactory {

    private NotificacaoServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static NotificacaoService create() {
        return new NotificacaoServiceImpl();
    }
}
