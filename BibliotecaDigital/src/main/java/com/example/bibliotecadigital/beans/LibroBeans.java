package com.example.bibliotecadigital.beans;

/**
 * Bean que representa un Libro.
 * Incluye campos adicionales (nombreAutor, nombreCategoria) útiles para mostrar en la tabla.
 */
public class LibroBeans {

    private int idLibro;
    private String titulo;
    private int anioPublicacion;
    private int idAutor;
    private int idCategoria;

    // Campos de apoyo para la visualización en JTable (JOIN)
    private String nombreAutor;
    private String nombreCategoria;

    public LibroBeans() {
    }

    public LibroBeans(int idLibro, String titulo, int anioPublicacion, int idAutor, int idCategoria) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
