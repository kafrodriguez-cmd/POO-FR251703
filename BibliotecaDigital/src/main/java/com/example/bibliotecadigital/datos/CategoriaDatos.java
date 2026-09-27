package com.example.bibliotecadigital.datos;

import com.example.bibliotecadigital.beans.CategoriaBeans;
import com.example.bibliotecadigital.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de datos para Categorías.
 */
public class CategoriaDatos {

    private static final String SQL_SELECT_ALL = "SELECT id_categoria, nombre_categoria FROM categoria ORDER BY nombre_categoria";

    /**
     * Obtiene todas las categorías para llenar el JComboBox.
     */
    public List<CategoriaBeans> listarTodas() {
        List<CategoriaBeans> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CategoriaBeans cat = new CategoriaBeans();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return lista;
    }
}
