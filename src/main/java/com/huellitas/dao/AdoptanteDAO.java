package com.huellitas.dao;

import com.huellitas.modelo.Adoptante;
import com.huellitas.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Adoptante.
 */
public class AdoptanteDAO {

    public List<Adoptante> listarTodos() {
        List<Adoptante> lista = new ArrayList<>();
        String sql = "SELECT * FROM adoptante ORDER BY apellidos, nombres";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs, ps);
        }
        return lista;
    }

    public List<Adoptante> buscarPorNombre(String criterio) {
        List<Adoptante> lista = new ArrayList<>();
        String sql = "SELECT * FROM adoptante WHERE nombres LIKE ? OR apellidos LIKE ? OR dui LIKE ? ORDER BY apellidos";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            String like = "%" + (criterio != null ? criterio.trim() : "") + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs, ps);
        }
        return lista;
    }

    public Adoptante buscarPorId(int id) {
        String sql = "SELECT * FROM adoptante WHERE id_adoptante = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs, ps);
        }
        return null;
    }

    public boolean insertar(Adoptante a) {
        String sql = "INSERT INTO adoptante (nombres, apellidos, dui, telefono, email, direccion) VALUES (?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getNombres());
            ps.setString(2, a.getApellidos());
            ps.setString(3, a.getDui());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getEmail());
            ps.setString(6, a.getDireccion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    public boolean actualizar(Adoptante a) {
        String sql = "UPDATE adoptante SET nombres=?, apellidos=?, dui=?, telefono=?, email=?, direccion=? WHERE id_adoptante=?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getNombres());
            ps.setString(2, a.getApellidos());
            ps.setString(3, a.getDui());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getEmail());
            ps.setString(6, a.getDireccion());
            ps.setInt(7, a.getIdAdoptante());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM adoptante WHERE id_adoptante = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    private Adoptante mapear(ResultSet rs) throws SQLException {
        Adoptante a = new Adoptante();
        a.setIdAdoptante(rs.getInt("id_adoptante"));
        a.setNombres(rs.getString("nombres"));
        a.setApellidos(rs.getString("apellidos"));
        a.setDui(rs.getString("dui"));
        a.setTelefono(rs.getString("telefono"));
        a.setEmail(rs.getString("email"));
        a.setDireccion(rs.getString("direccion"));
        a.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return a;
    }
}
