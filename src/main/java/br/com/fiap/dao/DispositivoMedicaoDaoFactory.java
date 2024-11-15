package br.com.fiap.dao;

public final class DispositivoMedicaoDaoFactory {

    private DispositivoMedicaoDaoFactory() {
        throw new UnsupportedOperationException("Essa classe não deve ser instanciada");
    }

    public static DispositivoMedicaoDao getDispositivoMedicaoDao() {
        return new DispositivoMedicaoDaoImpl();
    }
}

