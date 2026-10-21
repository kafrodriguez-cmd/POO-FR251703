package dao;

import models.CategoriaBean;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla categorias.
 * Único responsable de las consultas SQL relacionadas con categorías.
 */
public class CategoriaDAO {

    /**
     * Obtiene todas las categorías de la base de datos.
     * @return Lista de CategoriaBean
     */
    public List<CategoriaBean> listar() {
        List<CategoriaBean> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre_categoria FROM categorias ORDER BY nombre_categoria";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return lista;
    }

    /**
     * Obtiene una categoría por su ID.
     */
    public CategoriaBean obtenerPorId(int id) {
        CategoriaBean cat = null;
        String sql = "SELECT id_categoria, nombre_categoria FROM categorias WHERE id_categoria = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categoría: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(conn);
        }
        return cat;
    }
}
