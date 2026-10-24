package com.huellitas.modelo;

import java.io.Serializable;
import java.sql.Date;

/**
 * JavaBean que representa una Mascota disponible para adopción.
 */
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idMascota;
    private String nombre;
    private String especie;          // Perro, Gato, Conejo, Ave, Otro
    private String raza;
    private int edadAprox;           // en meses
    private String sexo;             // Macho / Hembra
    private String color;
    private String tamano;           // Pequeño / Mediano / Grande
    private String estado;           // Disponible, En proceso, Adoptada, No disponible
    private String descripcion;
    private Date fechaIngreso;
    private String fotoUrl;

    public Mascota() { }

    // ===================== GETTERS & SETTERS =====================

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdadAprox() { return edadAprox; }
    public void setEdadAprox(int edadAprox) { this.edadAprox = edadAprox; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    /** Retorna la edad en formato legible (años y meses) */
    public String getEdadLegible() {
        if (edadAprox <= 0) return "Desconocida";
        int anios = edadAprox / 12;
        int meses = edadAprox % 12;
        if (anios == 0) return meses + " mes(es)";
        if (meses == 0) return anios + " año(s)";
        return anios + " año(s) y " + meses + " mes(es)";
    }

    @Override
    public String toString() {
        return "Mascota{id=" + idMascota + ", nombre='" + nombre + "', especie='" + especie + "'}";
    }
}
