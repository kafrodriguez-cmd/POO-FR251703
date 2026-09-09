package com.example.pooguia7.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de conexión a MySQL (academiabdd).
 * Ajusta usuario/password si tu servidor lo requiere.
 */
public class Conexion {

    private Connection conexion = null;
    private Statement s = null;
    private ResultSet rs = null;

    // Cambia estos valores según tu entorno (WAMP/XAMPP normalmente root / "")
    private static final String URL  = "jdbc:mysql://localhost:3306/academiabdd?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "";

    public Conexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
            s = conexion.createStatement();
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL: " + e.getMessage());
        }
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(String sql) throws SQLException {
        this.rs = s.executeQuery(sql);
    }

    public void setQuery(String sql) throws SQLException {
        s.executeUpdate(sql);
    }

    public Connection getConnection() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (rs != null) rs.close();
            if (s != null) s.close();
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
