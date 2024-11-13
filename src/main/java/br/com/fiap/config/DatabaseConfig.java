package br.com.fiap.config;

final class DatabaseConfig {

    private DatabaseConfig() {
        throw new UnsupportedOperationException("DatabaseConfig não pode ser instanciada");
    }

    static String getUrl(){
        return "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    }

    static String getUser(){
        return "rm559064";
    }

    static String getPassword(){
        return "221005";
    }
}
