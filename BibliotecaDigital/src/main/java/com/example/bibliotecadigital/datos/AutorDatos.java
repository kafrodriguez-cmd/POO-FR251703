package com.example.bibliotecadigital.datos;

import com.example.bibliotecadigital.beans.AutorBeans;
import com.example.bibliotecadigital.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de datos para Autores.
 * Proporciona consultas para llenar ComboBox y listados.
 */
public class AutorDatos {

    private static final String SQL_SELECT_ALL = "SELECT id_autor, nombre, nacionalidad FROM autor ORDER BY nombre";

    /**
     * Obtiene todos los autores para llenar el JComboBox.
     */
    public List<AutorBeans> listarTodos() {
        List<AutorBeans> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AutorBeans autor = new AutorBeans();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                lista.add(autor);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar autores: " + e.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return lista;
    }

    /**
     * Busca un autor por su ID.
     */
    public AutorBeans buscarPorId(int idAutor) {
        AutorBeans autor = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id_autor, nombre, nacionalidad FROM autor WHERE id_autor = ?");
            stmt.setInt(1, idAutor);
            rs = stmt.executeQuery();

            if (rs.next()) {
                autor = new AutorBeans();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar autor: " + e.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return autor;
    }
}
