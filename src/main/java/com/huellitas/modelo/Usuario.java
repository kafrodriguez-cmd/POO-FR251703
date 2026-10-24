package com.huellitas.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * JavaBean que representa a un Usuario del sistema (Admin / Empleado).
 * Sigue las convenciones: atributos privados, constructor vacío, getters y setters.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuario;
    private String username;
    private String password;
    private String nombreCompleto;
    private String rol;                 // ADMIN | EMPLEADO
    private boolean activo;
    private Timestamp fechaRegistro;

    public Usuario() { }

    public Usuario(int idUsuario, String username, String nombreCompleto, String rol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.activo = true;
    }

    // ===================== GETTERS & SETTERS =====================

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(this.rol);
    }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", username='" + username + "', rol='" + rol + "'}";
    }
}
