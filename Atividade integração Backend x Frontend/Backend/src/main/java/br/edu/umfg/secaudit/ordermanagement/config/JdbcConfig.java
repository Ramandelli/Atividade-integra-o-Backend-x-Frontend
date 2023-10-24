package br.edu.umfg.secaudit.ordermanagement.config;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class JdbcConfig {

    public Connection connection() throws SQLException {
        var host = getEnv("DATABASE_HOST", "jdbc:postgresql://localhost:5432/pedido");
        var username = getEnv("DATABASE_USER", "admin");
        var password = getEnv("DATABASE_PASSWORD", "admin");
        return DriverManager.getConnection(host, username, password);
    }

    private String getEnv(String DATABASE_HOST, String defaultValue) {
        var value = System.getenv(DATABASE_HOST);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return defaultValue;
    }

}
