package br.com.empresa.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL =
        "jdbc:h2:mem:empresa_db;" +
        "DB_CLOSE_DELAY=-1;" +
        "INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("H2 Database carregado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar driver H2", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}