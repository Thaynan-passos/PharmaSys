package br.univ.pharmasys.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/pharmasys?useTimezone=true&serverTimezone=America/Recife";

    public static Connection getConnection() {
        String url = ConfigLoader.get("db.url", DEFAULT_URL);
        String user = ConfigLoader.get("db.user", "root");
        String password = ConfigLoader.get("db.password", "");

        try {
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.err.println("FALHA AO CONECTAR AO BANCO DE DADOS!");
            throw new RuntimeException("Erro de Conexão: " + e.getMessage(), e);
        }
    }
}