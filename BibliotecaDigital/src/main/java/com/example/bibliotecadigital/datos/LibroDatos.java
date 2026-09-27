package com.example.bibliotecadigital.datos;

import com.example.bibliotecadigital.beans.LibroBeans;
import com.example.bibliotecadigital.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de datos para Libros - CRUD completo usando PreparedStatement.
 */
public class LibroDatos {

    private static final String SQL_INSERT =
            "INSERT INTO libro (titulo, ano_publicacion, id_autor, id_categoria) VALUES (?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE libro SET titulo = ?, ano_publicacion = ?, id_autor = ?, id_categoria = ? WHERE id_libro = ?";

    private static final String SQL_DELETE =
            "DELETE FROM libro WHERE id_libro = ?";

    private static final String SQL_SELECT_ALL =
            "SELECT l.id_libro, l.titulo, l.ano_publicacion, l.id_autor, l.id_categoria, " +
            "a.nombre AS nombre_autor, c.nombre_categoria " +
            "FROM libro l " +
            "INNER JOIN autor a ON l.id_autor = a.id_autor " +
            "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
            "ORDER BY l.titulo";

    private static final String SQL_SELECT_BY_AUTOR =
            "SELECT l.id_libro, l.titulo, l.ano_publicacion, l.id_autor, l.id_categoria, " +
            "a.nombre AS nombre_autor, c.nombre_categoria " +
            "FROM libro l " +
            "INNER JOIN autor a ON l.id_autor = a.id_autor " +
            "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
            "WHERE l.id_autor = ? ORDER BY l.titulo";

    private static final String SQL_SELECT_BY_CATEGORIA =
            "SELECT l.id_libro, l.titulo, l.ano_publicacion, l.id_autor, l.id_categoria, " +
            "a.nombre AS nombre_autor, c.nombre_categoria " +
            "FROM libro l " +
            "INNER JOIN autor a ON l.id_autor = a.id_autor " +
            "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
            "WHERE l.id_categoria = ? ORDER BY l.titulo";

    /**
     * Inserta un nuevo libro.
     * @return true si se insertó correctamente
     */
    public boolean insertar(LibroBeans libro) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, libro.getTitulo());
            stmt.setInt(2, libro.getAnioPublicacion());
            stmt.setInt(3, libro.getIdAutor());
            stmt.setInt(4, libro.getIdCategoria());

            int filas = stmt.executeUpdate();
            exito = filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Actualiza un libro existente.
     */
    public boolean actualizar(LibroBeans libro) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, libro.getTitulo());
            stmt.setInt(2, libro.getAnioPublicacion());
            stmt.setInt(3, libro.getIdAutor());
            stmt.setInt(4, libro.getIdCategoria());
            stmt.setInt(5, libro.getIdLibro());

            int filas = stmt.executeUpdate();
            exito = filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Elimina un libro por su ID.
     */
    public boolean eliminar(int idLibro) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idLibro);

            int filas = stmt.executeUpdate();
            exito = filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Lista todos los libros (con JOIN para mostrar nombres).
     */
    public List<LibroBeans> listarTodos() {
        return ejecutarConsulta(SQL_SELECT_ALL, null);
    }

    /**
     * Filtra libros por autor.
     */
    public List<LibroBeans> listarPorAutor(int idAutor) {
        return ejecutarConsulta(SQL_SELECT_BY_AUTOR, idAutor);
    }

    /**
     * Filtra libros por categoría.
     */
    public List<LibroBeans> listarPorCategoria(int idCategoria) {
        return ejecutarConsulta(SQL_SELECT_BY_CATEGORIA, idCategoria);
    }

    /**
     * Método auxiliar para ejecutar las consultas SELECT.
     */
    private List<LibroBeans> ejecutarConsulta(String sql, Integer parametro) {
        List<LibroBeans> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            if (parametro != null) {
                stmt.setInt(1, parametro);
            }
            rs = stmt.executeQuery();

            while (rs.next()) {
                LibroBeans libro = new LibroBeans();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAnioPublicacion(rs.getInt("ano_publicacion"));
                libro.setIdAutor(rs.getInt("id_autor"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setNombreAutor(rs.getString("nombre_autor"));
                libro.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar libros: " + e.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return lista;
    }
}
