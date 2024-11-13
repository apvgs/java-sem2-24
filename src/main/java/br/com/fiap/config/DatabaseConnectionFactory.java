package br.com.fiap.config;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionFactory {

    private DatabaseConnectionFactory() {
        throw new UnsupportedOperationException("DatabaseConnectionFactory não pode ser instanciada");
    }

    public static Connection getConnection() throws SQLException {
        return DatabaseConnectionImpl.getInstance().getConnection();
    }
}
