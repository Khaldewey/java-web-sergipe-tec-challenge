package br.com.empresa.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL =
    "jdbc:h2:file:./data/empresa_db;" +
    "AUTO_SERVER=TRUE;" +
    "INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("H2 persistente carregado!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}