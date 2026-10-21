package models;

import java.io.Serializable;

/**
 * JavaBean que representa la tabla estudiantes.
 */
public class EstudianteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idEstudiante;
    private String carnet;
    private String nombreEstudiante;
    private String carrera;
    private String telefono;

    // Constructor vacío
    public EstudianteBean() {
    }

    public EstudianteBean(int idEstudiante, String carnet, String nombreEstudiante,
                          String carrera, String telefono) {
        this.idEstudiante = idEstudiante;
        this.carnet = carnet;
        this.nombreEstudiante = nombreEstudiante;
        this.carrera = carrera;
        this.telefono = telefono;
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return "EstudianteBean{" +
                "idEstudiante=" + idEstudiante +
                ", carnet='" + carnet + '\'' +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                '}';
    }
}
