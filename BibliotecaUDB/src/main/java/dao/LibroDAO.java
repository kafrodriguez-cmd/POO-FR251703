package dao;

import models.CategoriaBean;
import models.LibroBean;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla libros.
 * Contiene todos los métodos de acceso a datos de libros.
 */
public class LibroDAO {

    /**
     * Lista todos los libros con su categoría asociada.
     */
    public List<LibroBean> listar() {
        List<LibroBean> lista = new ArrayList<>();
        String sql = "SELECT l.id_libro, l.titulo, l.autor, l.isbn, l.id_categoria, " +
                     "l.cantidad_disponible, c.nombre_categoria " +
                     "FROM libros l " +
                     "INNER JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "ORDER BY l.titulo";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LibroBean libro = new LibroBean();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));

                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                libro.setCategoria(cat);

                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return lista;
    }

    /**
     * Registra un nuevo libro.
     * @return true si se insertó correctamente
     */
    public boolean registrar(LibroBean libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, id_categoria, cantidad_disponible) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setInt(4, libro.getIdCategoria());
            ps.setInt(5, libro.getCantidadDisponible());

            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Obtiene un libro por su identificador.
     */
    public LibroBean obtenerPorId(int id) {
        LibroBean libro = null;
        String sql = "SELECT l.id_libro, l.titulo, l.autor, l.isbn, l.id_categoria, " +
                     "l.cantidad_disponible, c.nombre_categoria " +
                     "FROM libros l " +
                     "INNER JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.id_libro = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                libro = new LibroBean();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));

                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                libro.setCategoria(cat);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return libro;
    }

    /**
     * Actualiza la cantidad disponible de un libro.
     */
    public boolean actualizarCantidad(int idLibro, int nuevaCantidad) {
        String sql = "UPDATE libros SET cantidad_disponible = ? WHERE id_libro = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, idLibro);

            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cantidad: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Valida si un libro tiene ejemplares disponibles.
     * @return true si cantidad_disponible > 0
     */
    public boolean validarDisponibilidad(int idLibro) {
        String sql = "SELECT cantidad_disponible FROM libros WHERE id_libro = ?";
        boolean disponible = false;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idLibro);
            rs = ps.executeQuery();

            if (rs.next()) {
                disponible = rs.getInt("cantidad_disponible") > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar disponibilidad: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return disponible;
    }

    /**
     * Lista solo los libros que tienen cantidad disponible > 0 (para préstamos).
     */
    public List<LibroBean> listarDisponibles() {
        List<LibroBean> lista = new ArrayList<>();
        String sql = "SELECT l.id_libro, l.titulo, l.autor, l.isbn, l.id_categoria, " +
                     "l.cantidad_disponible, c.nombre_categoria " +
                     "FROM libros l " +
                     "INNER JOIN categorias c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.cantidad_disponible > 0 " +
                     "ORDER BY l.titulo";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LibroBean libro = new LibroBean();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));

                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                libro.setCategoria(cat);

                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros disponibles: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return lista;
    }
}
