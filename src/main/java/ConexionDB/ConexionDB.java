/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Laptop
 */
public class ConexionDB {
    private static final String url;
    private static final String user;
    private static final String password;
    private static final String driver;

    static {
        try (InputStream input = ConexionDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo db.properties");
            }
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            // Registrar driver
            Class.forName(driver);

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Error cargando configuración de BD: " + ex.getMessage(), ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

