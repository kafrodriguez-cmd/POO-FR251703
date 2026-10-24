package com.huellitas.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * JavaBean que representa a un Adoptante (persona interesada en adoptar).
 */
public class Adoptante implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idAdoptante;
    private String nombres;
    private String apellidos;
    private String dui;
    private String telefono;
    private String email;
    private String direccion;
    private Timestamp fechaRegistro;

    public Adoptante() { }

    // ===================== GETTERS & SETTERS =====================

    public int getIdAdoptante() { return idAdoptante; }
    public void setIdAdoptante(int idAdoptante) { this.idAdoptante = idAdoptante; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    /** Nombre completo del adoptante */
    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }

    @Override
    public String toString() {
        return "Adoptante{id=" + idAdoptante + ", nombre='" + getNombreCompleto() + "'}";
    }
}
