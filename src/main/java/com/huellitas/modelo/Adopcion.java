package com.huellitas.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * JavaBean que representa una Adopción.
 * Relaciona Mascota (1) con Adoptante (N) y Usuario que registró.
 */
public class Adopcion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAdopcion;
    private int idMascota;
    private int idAdoptante;
    private Date fechaAdopcion;
    private String estado;              // Pendiente, Aprobada, Rechazada, Finalizada
    private String observaciones;
    private int idUsuario;
    private Timestamp fechaRegistro;

    // Campos de apoyo para JOINs (no están en la tabla, se llenan desde consultas)
    private String nombreMascota;
    private String especieMascota;
    private String razaMascota;
    private String nombreAdoptante;
    private String duiAdoptante;
    private String telefonoAdoptante;
    private String registradoPor;

    public Adopcion() { }

    // ===================== GETTERS & SETTERS =====================

    public int getIdAdopcion() { return idAdopcion; }
    public void setIdAdopcion(int idAdopcion) { this.idAdopcion = idAdopcion; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdAdoptante() { return idAdoptante; }
    public void setIdAdoptante(int idAdoptante) { this.idAdoptante = idAdoptante; }

    public Date getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(Date fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // Campos de JOIN
    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

    public String getEspecieMascota() { return especieMascota; }
    public void setEspecieMascota(String especieMascota) { this.especieMascota = especieMascota; }

    public String getRazaMascota() { return razaMascota; }
    public void setRazaMascota(String razaMascota) { this.razaMascota = razaMascota; }

    public String getNombreAdoptante() { return nombreAdoptante; }
    public void setNombreAdoptante(String nombreAdoptante) { this.nombreAdoptante = nombreAdoptante; }

    public String getDuiAdoptante() { return duiAdoptante; }
    public void setDuiAdoptante(String duiAdoptante) { this.duiAdoptante = duiAdoptante; }

    public String getTelefonoAdoptante() { return telefonoAdoptante; }
    public void setTelefonoAdoptante(String telefonoAdoptante) { this.telefonoAdoptante = telefonoAdoptante; }

    public String getRegistradoPor() { return registradoPor; }
    public void setRegistradoPor(String registradoPor) { this.registradoPor = registradoPor; }

    @Override
    public String toString() {
        return "Adopcion{id=" + idAdopcion + ", mascota=" + nombreMascota + ", adoptante=" + nombreAdoptante + "}";
    }
}
