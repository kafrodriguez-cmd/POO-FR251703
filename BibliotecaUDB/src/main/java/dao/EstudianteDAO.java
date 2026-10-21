package dao;

import models.EstudianteBean;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla estudiantes.
 */
public class EstudianteDAO {

    /**
     * Lista todos los estudiantes.
     */
    public List<EstudianteBean> listar() {
        List<EstudianteBean> lista = new ArrayList<>();
        String sql = "SELECT id_estudiante, carnet, nombre_estudiante, carrera, telefono " +
                     "FROM estudiantes ORDER BY nombre_estudiante";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                EstudianteBean est = new EstudianteBean();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setCarnet(rs.getString("carnet"));
                est.setNombreEstudiante(rs.getString("nombre_estudiante"));
                est.setCarrera(rs.getString("carrera"));
                est.setTelefono(rs.getString("telefono"));
                lista.add(est);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return lista;
    }

    /**
     * Registra un nuevo estudiante.
     * @return true si se insertó correctamente
     */
    public boolean registrar(EstudianteBean estudiante) {
        String sql = "INSERT INTO estudiantes (carnet, nombre_estudiante, carrera, telefono) " +
                     "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, estudiante.getCarnet());
            ps.setString(2, estudiante.getNombreEstudiante());
            ps.setString(3, estudiante.getCarrera());
            ps.setString(4, estudiante.getTelefono());

            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar estudiante: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return exito;
    }

    /**
     * Obtiene un estudiante por su ID.
     */
    public EstudianteBean obtenerPorId(int id) {
        EstudianteBean est = null;
        String sql = "SELECT id_estudiante, carnet, nombre_estudiante, carrera, telefono " +
                     "FROM estudiantes WHERE id_estudiante = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                est = new EstudianteBean();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setCarnet(rs.getString("carnet"));
                est.setNombreEstudiante(rs.getString("nombre_estudiante"));
                est.setCarrera(rs.getString("carrera"));
                est.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estudiante: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return est;
    }
}
