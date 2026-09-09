package com.example.pooguia7.modelos;

public class Alumno {
    private int codAlumno;
    private String nombre;
    private String apellido;
    private int edad;
    private String direccion;

    public Alumno() {}

    public Alumno(int codAlumno, String nombre, String apellido, int edad, String direccion) {
        this.codAlumno = codAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
    }

    public int getCodAlumno() { return codAlumno; }
    public void setCodAlumno(int codAlumno) { this.codAlumno = codAlumno; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return codAlumno + " - " + nombre + " " + apellido + " (" + edad + " años) - " + direccion;
    }
}
