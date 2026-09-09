package com.example.pooguia7.modelos;

public class Materia {
    private int codMateria;
    private String nombre;
    private String descripcion;

    public Materia() {}

    public Materia(int codMateria, String nombre, String descripcion) {
        this.codMateria = codMateria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCodMateria() { return codMateria; }
    public void setCodMateria(int codMateria) { this.codMateria = codMateria; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return codMateria + " - " + nombre + ": " + descripcion;
    }
}
