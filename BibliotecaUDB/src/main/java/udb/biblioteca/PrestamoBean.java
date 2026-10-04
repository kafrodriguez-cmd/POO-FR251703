package udb.biblioteca;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean que representa un préstamo de libro.
 * Contiene objetos LibroBean y EstudianteBean para representar las relaciones.
 * Incluye getEstadoPrestamo() que determina si está vigente o vencido.
 * Validación: no permite prestar libros con cantidad disponible = 0.
 */
public class PrestamoBean {

    // Atributos privados
    private int idPrestamo;
    private int idEstudiante;
    private int idLibro;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String estado;
    private LibroBean libro;           // Relación con LibroBean
    private EstudianteBean estudiante; // Relación con EstudianteBean

    /**
     * Constructor sin argumentos
     */
    public PrestamoBean() {
        this.idPrestamo = 0;
        this.idEstudiante = 0;
        this.idLibro = 0;
        this.fechaPrestamo = null;
        this.fechaDevolucion = null;
        this.estado = "Activo";
        this.libro = new LibroBean();
        this.estudiante = new EstudianteBean();
    }

    // ========== Getters y Setters ==========

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    /**
     * Recibe la fecha como String desde el formulario HTML (type="date" → yyyy-MM-dd).
     * Evita el error de conversión de jsp:setProperty con java.sql.Date.
     */
    public void setFechaPrestamo(String fechaPrestamo) {
        if (fechaPrestamo != null && !fechaPrestamo.trim().isEmpty()) {
            try {
                this.fechaPrestamo = Date.valueOf(fechaPrestamo.trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Formato de fecha de préstamo inválido: " + fechaPrestamo);
                this.fechaPrestamo = null;
            }
        }
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * Recibe la fecha como String desde el formulario HTML (type="date" → yyyy-MM-dd).
     */
    public void setFechaDevolucion(String fechaDevolucion) {
        if (fechaDevolucion != null && !fechaDevolucion.trim().isEmpty()) {
            try {
                this.fechaDevolucion = Date.valueOf(fechaDevolucion.trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Formato de fecha de devolución inválido: " + fechaDevolucion);
                this.fechaDevolucion = null;
            }
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LibroBean getLibro() {
        return libro;
    }

    public void setLibro(LibroBean libro) {
        this.libro = libro;
    }

    public EstudianteBean getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteBean estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * Determina si el préstamo está vigente o vencido según la fecha actual.
     * @return "Vigente", "Vencido" o el estado actual si ya está Devuelto
     */
    public String getEstadoPrestamo() {
        if ("Devuelto".equalsIgnoreCase(this.estado)) {
            return "Devuelto";
        }
        if (this.fechaDevolucion == null) {
            return this.estado;
        }
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDev = this.fechaDevolucion.toLocalDate();
        if (hoy.isAfter(fechaDev)) {
            return "Vencido";
        }
        return "Vigente";
    }

    /**
     * Valida que el libro tenga cantidad disponible > 0 antes de prestar.
     * @return true si se puede prestar, false en caso contrario
     */
    public boolean validarDisponibilidad() {
        LibroBean lb = new LibroBean();
        lb.setIdLibro(this.idLibro);
        int cantidad = lb.consultarCantidadDisponible();
        return cantidad > 0;
    }

    /**
     * Inserta un nuevo préstamo, validando disponibilidad y decrementando cantidad.
     * @return mensaje de resultado
     */
    public String insertar() {
        // Validación de disponibilidad (funcionalidad extra)
        if (!validarDisponibilidad()) {
            return "ERROR: El libro seleccionado no tiene ejemplares disponibles.";
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, estado) "
                       + "VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.idEstudiante);
            ps.setInt(2, this.idLibro);
            ps.setDate(3, this.fechaPrestamo);
            ps.setDate(4, this.fechaDevolucion);
            ps.setString(5, this.estado != null ? this.estado : "Activo");

            int filas = ps.executeUpdate();
            if (filas > 0) {
                // Decrementar cantidad disponible del libro
                LibroBean lb = new LibroBean();
                lb.setIdLibro(this.idLibro);
                lb.decrementarCantidad();
                return "OK";
            }
            return "ERROR: No se pudo registrar el préstamo.";
        } catch (SQLException e) {
            System.err.println("Error al insertar préstamo: " + e.getMessage());
            return "ERROR: " + e.getMessage();
        } finally {
            cerrarRecursos(null, ps, conn);
        }
    }

    /**
     * Realiza la devolución de un préstamo: cambia estado a "Devuelto"
     * e incrementa la cantidad disponible del libro.
     * @return true si la devolución fue exitosa
     */
    public boolean devolver() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            // Obtener el id_libro del préstamo
            String sqlSelect = "SELECT id_libro FROM prestamos WHERE id_prestamo = ? AND estado != 'Devuelto'";
            ps = conn.prepareStatement(sqlSelect);
            ps.setInt(1, this.idPrestamo);
            ResultSet rs = ps.executeQuery();

            int idLibroPrestamo = 0;
            if (rs.next()) {
                idLibroPrestamo = rs.getInt("id_libro");
            }
            rs.close();
            ps.close();

            if (idLibroPrestamo == 0) {
                return false; // Ya devuelto o no existe
            }

            // Actualizar estado a Devuelto
            String sqlUpdate = "UPDATE prestamos SET estado = 'Devuelto' WHERE id_prestamo = ?";
            ps = conn.prepareStatement(sqlUpdate);
            ps.setInt(1, this.idPrestamo);
            int filas = ps.executeUpdate();
            ps.close();

            if (filas > 0) {
                // Incrementar cantidad del libro
                LibroBean lb = new LibroBean();
                lb.setIdLibro(idLibroPrestamo);
                lb.incrementarCantidad();
                exito = true;
            }
        } catch (SQLException e) {
            System.err.println("Error al devolver préstamo: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

    /**
     * Obtiene la lista completa de préstamos con datos de estudiante y libro.
     * @return Lista de PrestamoBean
     */
    public List<PrestamoBean> getListaPrestamos() {
        List<PrestamoBean> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT p.id_prestamo, p.id_estudiante, p.id_libro, "
                       + "p.fecha_prestamo, p.fecha_devolucion, p.estado, "
                       + "e.carnet, e.nombre_estudiante, e.carrera, "
                       + "l.titulo, l.autor, l.isbn "
                       + "FROM prestamos p "
                       + "INNER JOIN estudiantes e ON p.id_estudiante = e.id_estudiante "
                       + "INNER JOIN libros l ON p.id_libro = l.id_libro "
                       + "ORDER BY p.fecha_prestamo DESC";
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

                EstudianteBean est = new EstudianteBean();
                est.setIdEstudiante(rs.getInt("id_estudiante"));
                est.setCarnet(rs.getString("carnet"));
                est.setNombreEstudiante(rs.getString("nombre_estudiante"));
                est.setCarrera(rs.getString("carrera"));
                prestamo.setEstudiante(est);

                LibroBean lib = new LibroBean();
                lib.setIdLibro(rs.getInt("id_libro"));
                lib.setTitulo(rs.getString("titulo"));
                lib.setAutor(rs.getString("autor"));
                lib.setIsbn(rs.getString("isbn"));
                prestamo.setLibro(lib);

                lista.add(prestamo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de préstamos: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return lista;
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
