package models;

import java.io.Serializable;
import java.sql.Date;

/**
 * JavaBean que representa la tabla prestamos.
 * Incluye objetos LibroBean y EstudianteBean para representar las relaciones.
 */
public class PrestamoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idPrestamo;
    private int idEstudiante;       // FK
    private int idLibro;            // FK
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private String estado;

    // Objetos relacionados
    private EstudianteBean estudiante;
    private LibroBean libro;

    // Constructor vacío
    public PrestamoBean() {
        this.estudiante = new EstudianteBean();
        this.libro = new LibroBean();
    }

    public PrestamoBean(int idPrestamo, int idEstudiante, int idLibro,
                        Date fechaPrestamo, Date fechaDevolucion, String estado) {
        this.idPrestamo = idPrestamo;
        this.idEstudiante = idEstudiante;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.estudiante = new EstudianteBean();
        this.libro = new LibroBean();
    }

    // Getters y Setters
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

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EstudianteBean getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(EstudianteBean estudiante) {
        this.estudiante = estudiante;
    }

    public LibroBean getLibro() {
        return libro;
    }

    public void setLibro(LibroBean libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "PrestamoBean{" +
                "idPrestamo=" + idPrestamo +
                ", estado='" + estado + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                '}';
    }
}
