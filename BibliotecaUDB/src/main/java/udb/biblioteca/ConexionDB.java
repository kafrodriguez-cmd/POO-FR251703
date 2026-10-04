package udb.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos MySQL.
 * Utiliza JDBC con PreparedStatement (recomendado para prevenir inyección SQL).
 *
 * IMPORTANTE: Ajuste los parámetros de conexión según su entorno local:
 * - URL, usuario y contraseña de MySQL
 */
public class ConexionDB {

    // Parámetros de conexión - AJUSTAR SEGÚN SU ENTORNO
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotecaudb"
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
            + "&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Cambiar por su contraseña de MySQL

    /**
     * Obtiene una conexión a la base de datos bibliotecaudb.
     * @return Connection activa
     * @throws SQLException si ocurre un error de conexión
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL (Connector/J 8.x)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado. Verifique la dependencia en pom.xml", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
