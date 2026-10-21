package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase utilitaria para manejar la conexión a la base de datos MySQL.
 * Utiliza el patrón Singleton + synchronized para evitar problemas de concurrencia.
 * 
 * Configuración: bibliotecaudb en localhost:3306
 * Usuario: root  |  Password: (vacía por defecto en WAMP/XAMPP)
 */
public class Conexion {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bibliotecaudb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";   // Cambiar si tiene contraseña

    private static Connection con = null;

    /**
     * Obtiene una conexión a la base de datos de forma segura (thread-safe).
     * @return Connection activa
     * @throws SQLException si ocurre un error de conexión
     */
    public static synchronized Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC de MySQL");
            e.printStackTrace();
            throw new SQLException("Driver no encontrado", e);
        }
        return con;
    }

    /**
     * Cierra un ResultSet de forma segura.
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra un Statement de forma segura.
     */
    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra una Connection de forma segura.
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
