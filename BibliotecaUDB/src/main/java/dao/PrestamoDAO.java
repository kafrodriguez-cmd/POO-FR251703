package dao;

import models.EstudianteBean;
import models.LibroBean;
import models.PrestamoBean;
import util.Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla prestamos.
 * Incluye métodos para registrar, listar, devolver y actualizar estado.
 */
public class PrestamoDAO {

    /**
     * Registra un nuevo préstamo y disminuye la cantidad disponible del libro.
     */
    public boolean registrar(PrestamoBean prestamo) {
        String sqlInsert = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, estado) " +
                           "VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE libros SET cantidad_disponible = cantidad_disponible - 1 WHERE id_libro = ?";

        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false); // Transacción

            // Insertar préstamo
            psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, prestamo.getIdEstudiante());
            psInsert.setInt(2, prestamo.getIdLibro());
            psInsert.setDate(3, prestamo.getFechaPrestamo());
            psInsert.setDate(4, prestamo.getFechaDevolucion());
            psInsert.setString(5, prestamo.getEstado() != null ? prestamo.getEstado() : "Activo");
            psInsert.executeUpdate();

            // Disminuir cantidad disponible
            psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, prestamo.getIdLibro());
            psUpdate.executeUpdate();

            conn.commit();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Conexion.close(psInsert);
            Conexion.close(psUpdate);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Lista todos los préstamos con información completa del estudiante y del libro.
     */
    public List<PrestamoBean> listar() {
        List<PrestamoBean> lista = new ArrayList<>();
        String sql = "SELECT p.id_prestamo, p.id_estudiante, p.id_libro, " +
                     "p.fecha_prestamo, p.fecha_devolucion, p.estado, " +
                     "e.carnet, e.nombre_estudiante, e.carrera, " +
                     "l.titulo, l.autor, l.isbn " +
                     "FROM prestamos p " +
                     "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante " +
                     "INNER JOIN libros l ON p.id_libro = l.id_libro " +
                     "ORDER BY p.fecha_prestamo DESC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                PrestamoBean prestamo = new PrestamoBean();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setIdEstudiante(rs.getInt("id_estudiante"));
                prestamo.setIdLibro(rs.getInt("id_libro"));
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                prestamo.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                prestamo.setEstado(rs.getString("estado"));

                // Estudiante
                EstudianteBean est = new EstudianteBean();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setCarnet(rs.getString("carnet"));
                est.setNombreEstudiante(rs.getString("nombre_estudiante"));
                est.setCarrera(rs.getString("carrera"));
                prestamo.setEstudiante(est);

                // Libro
                LibroBean libro = new LibroBean();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                prestamo.setLibro(libro);

                lista.add(prestamo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return lista;
    }

    /**
     * Procesa la devolución de un préstamo:
     * - Cambia el estado a 'Devuelto'
     * - Incrementa la cantidad disponible del libro
     */
    public boolean registrarDevolucion(int idPrestamo) {
        String sqlEstado = "UPDATE prestamos SET estado = 'Devuelto' WHERE id_prestamo = ?";
        String sqlCantidad = "UPDATE libros SET cantidad_disponible = cantidad_disponible + 1 " +
                             "WHERE id_libro = (SELECT id_libro FROM prestamos WHERE id_prestamo = ?)";

        Connection conn = null;
        PreparedStatement psEstado = null;
        PreparedStatement psCantidad = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);

            // Actualizar estado
            psEstado = conn.prepareStatement(sqlEstado);
            psEstado.setInt(1, idPrestamo);
            psEstado.executeUpdate();

            // Incrementar cantidad
            psCantidad = conn.prepareStatement(sqlCantidad);
            psCantidad.setInt(1, idPrestamo);
            psCantidad.executeUpdate();

            conn.commit();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Error al registrar devolución: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Conexion.close(psEstado);
            Conexion.close(psCantidad);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Actualiza el estado de un préstamo.
     */
    public boolean actualizarEstado(int idPrestamo, String nuevoEstado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id_prestamo = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPrestamo);

            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }
}
