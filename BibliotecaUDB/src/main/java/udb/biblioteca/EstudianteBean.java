package udb.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean que representa un estudiante de la universidad.
 * Encapsula la lógica de acceso a la tabla estudiantes.
 */
public class EstudianteBean {

    // Atributos privados
    private int idEstudiante;
    private String carnet;
    private String nombreEstudiante;
    private String carrera;
    private String telefono;

    /**
     * Constructor sin argumentos
     */
    public EstudianteBean() {
        this.idEstudiante = 0;
        this.carnet = "";
        this.nombreEstudiante = "";
        this.carrera = "";
        this.telefono = "";
    }

    // ========== Getters y Setters ==========

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la lista completa de estudiantes.
     * @return Lista de EstudianteBean
     */
    public List<EstudianteBean> getListaEstudiantes() {
        List<EstudianteBean> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT id_estudiante, carnet, nombre_estudiante, carrera, telefono "
                       + "FROM estudiantes ORDER BY nombre_estudiante";
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
            System.err.println("Error al obtener lista de estudiantes: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return lista;
    }

    /**
     * Inserta un nuevo estudiante en la base de datos.
     * @return true si la inserción fue exitosa
     */
    public boolean insertar() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            String sql = "INSERT INTO estudiantes (carnet, nombre_estudiante, carrera, telefono) "
                       + "VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.carnet);
            ps.setString(2, this.nombreEstudiante);
            ps.setString(3, this.carrera);
            ps.setString(4, this.telefono);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar estudiante: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

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
