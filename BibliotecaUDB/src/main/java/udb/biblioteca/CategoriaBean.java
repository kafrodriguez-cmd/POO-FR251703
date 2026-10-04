package udb.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean que representa una categoría de libros.
 * Encapsula la lógica de acceso a la tabla categorias.
 * Convenciones: constructor sin argumentos, atributos privados, getters/setters públicos.
 */
public class CategoriaBean {

    // Atributos privados (encapsulamiento)
    private int idCategoria;
    private String nombreCategoria;

    /**
     * Constructor sin argumentos (requerido por la especificación de JavaBeans)
     */
    public CategoriaBean() {
        this.idCategoria = 0;
        this.nombreCategoria = "";
    }

    // ========== Getters y Setters ==========

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene la lista completa de categorías desde la base de datos.
     * @return Lista de objetos CategoriaBean
     */
    public List<CategoriaBean> getListaCategorias() {
        List<CategoriaBean> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT id_categoria, nombre_categoria FROM categorias ORDER BY nombre_categoria";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de categorías: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return lista;
    }

    /**
     * Inserta una nueva categoría en la base de datos.
     * @return true si la inserción fue exitosa
     */
    public boolean insertar() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            String sql = "INSERT INTO categorias (nombre_categoria) VALUES (?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.nombreCategoria);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

    /**
     * Cierra los recursos JDBC de forma segura.
     */
    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
