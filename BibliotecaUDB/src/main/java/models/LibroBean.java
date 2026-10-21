package models;

import java.io.Serializable;

/**
 * JavaBean que representa la tabla libros.
 * Incluye un objeto CategoriaBean para representar la relación FK.
 */
public class LibroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idLibro;
    private String titulo;
    private String autor;
    private String isbn;
    private int idCategoria;          // FK
    private int cantidadDisponible;
    private CategoriaBean categoria;  // Objeto relacionado

    // Constructor vacío
    public LibroBean() {
        this.categoria = new CategoriaBean();
    }

    public LibroBean(int idLibro, String titulo, String autor, String isbn,
                     int idCategoria, int cantidadDisponible) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.idCategoria = idCategoria;
        this.cantidadDisponible = cantidadDisponible;
        this.categoria = new CategoriaBean();
    }

    // Getters y Setters
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public CategoriaBean getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaBean categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "LibroBean{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
}
