package com.huellitas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase centralizada para la gestión de conexiones a MySQL.
 * Sigue el patrón Singleton básico y métodos de cierre seguros.
 * No se deben abrir conexiones sueltas en los Servlets ni en los DAO.
 */
public class Conexion {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL    = "jdbc:mysql://localhost:3306/huellitas_felices"
                                       + "?useSSL=false&serverTimezone=America/El_Salvador"
                                       + "&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    private static final String USER   = "root";
    private static final String PASS   = "";          // Cambiar según el entorno

    private static Connection conexion = null;

    private Conexion() { /* Constructor privado - no instanciable */ }

    /**
     * Obtiene una conexión activa a la base de datos.
     * Si la conexión actual está cerrada o es nula, crea una nueva.
     */
    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName(DRIVER);
                conexion = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL: " + e.getMessage(), e);
        }
        return conexion;
    }

    /** Cierra un ResultSet de forma segura */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { /* ignorar */ }
        }
    }

    /** Cierra un PreparedStatement de forma segura */
    public static void close(PreparedStatement ps) {
        if (ps != null) {
            try { ps.close(); } catch (SQLException e) { /* ignorar */ }
        }
    }

    /** Cierra la conexión de forma segura */
    public static void close(Connection con) {
        if (con != null) {
            try { con.close(); } catch (SQLException e) { /* ignorar */ }
        }
    }

    /** Cierra ResultSet + PreparedStatement */
    public static void close(ResultSet rs, PreparedStatement ps) {
        close(rs);
        close(ps);
    }

    /** Cierra todo: ResultSet + PreparedStatement + Connection */
    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        close(rs);
        close(ps);
        close(con);
    }
}
