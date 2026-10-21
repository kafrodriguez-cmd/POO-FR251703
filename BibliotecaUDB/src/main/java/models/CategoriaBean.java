package models;

import java.io.Serializable;

/**
 * JavaBean que representa la tabla categorias.
 * Cumple con las convenciones: constructor vacío, atributos privados y getters/setters.
 */
public class CategoriaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idCategoria;
    private String nombreCategoria;

    // Constructor vacío (requerido por JavaBean)
    public CategoriaBean() {
    }

    public CategoriaBean(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    // Getters y Setters
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        return "CategoriaBean{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }
}
