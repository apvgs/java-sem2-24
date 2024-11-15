package br.com.fiap.service;

public class DispositivoMedicaoServiceFactory {

    private DispositivoMedicaoServiceFactory() {
        throw new UnsupportedOperationException("Classe factory");
    }

    public static DispositivoMedicaoService create() {
        return new DispositivoMedicaoSeviceImpl();
    }
}
