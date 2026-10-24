package com.huellitas.dao;

import com.huellitas.modelo.Adopcion;
import com.huellitas.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Adopcion.
 * Incluye consultas con JOIN para reportes.
 */
public class AdopcionDAO {

    /**
     * Lista todas las adopciones con datos de mascota y adoptante (JOIN).
     * Cumple el requisito de al menos una consulta con JOIN.
     */
    public List<Adopcion> listarTodasConDetalle() {
        List<Adopcion> lista = new ArrayList<>();
        String sql = "SELECT a.id_adopcion, a.fecha_adopcion, a.estado, a.observaciones, a.id_usuario, a.fecha_registro, "
                   + "m.id_mascota, m.nombre AS nombre_mascota, m.especie, m.raza, "
                   + "ad.id_adoptante, CONCAT(ad.nombres,' ',ad.apellidos) AS nombre_adoptante, ad.dui, ad.telefono, "
                   + "u.nombre_completo AS registrado_por "
                   + "FROM adopcion a "
                   + "INNER JOIN mascota   m  ON a.id_mascota   = m.id_mascota "
                   + "INNER JOIN adoptante ad ON a.id_adoptante = ad.id_adoptante "
                   + "LEFT  JOIN usuario   u  ON a.id_usuario   = u.id_usuario "
                   + "ORDER BY a.fecha_adopcion DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearConDetalle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs, ps);
        }
        return lista;
    }

    public Adopcion buscarPorId(int id) {
        String sql = "SELECT a.*, m.nombre AS nombre_mascota, m.especie, m.raza, "
                   + "CONCAT(ad.nombres,' ',ad.apellidos) AS nombre_adoptante, ad.dui, ad.telefono, "
                   + "u.nombre_completo AS registrado_por "
                   + "FROM adopcion a "
                   + "INNER JOIN mascota   m  ON a.id_mascota   = m.id_mascota "
                   + "INNER JOIN adoptante ad ON a.id_adoptante = ad.id_adoptante "
                   + "LEFT  JOIN usuario   u  ON a.id_usuario   = u.id_usuario "
                   + "WHERE a.id_adopcion = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapearConDetalle(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs, ps);
        }
        return null;
    }

public boolean insertar(Adopcion a) {
    String sqlAdopcion = "INSERT INTO adopcion (id_mascota, id_adoptante, fecha_adopcion, estado, observaciones, id_usuario) VALUES (?,?,?,?,?,?)";
    String sqlMascota = "UPDATE mascota SET estado = 'Adoptada' WHERE id_mascota = ?";
    
    Connection con = null;
    PreparedStatement psAdopcion = null;
    PreparedStatement psMascota = null;
    
    try {
        con = Conexion.getConnection();
        con.setAutoCommit(false); // Inicia la transacción

        // 1. Registrar la adopción
        psAdopcion = con.prepareStatement(sqlAdopcion);
        psAdopcion.setInt(1, a.getIdMascota());
        psAdopcion.setInt(2, a.getIdAdoptante());
        psAdopcion.setDate(3, a.getFechaAdopcion());
        psAdopcion.setString(4, a.getEstado() != null ? a.getEstado() : "Finalizada");
        psAdopcion.setString(5, a.getObservaciones());
        psAdopcion.setInt(6, a.getIdUsuario());
        int filasAdopcion = psAdopcion.executeUpdate();

        // 2. Cambiar automáticamente el estado de la mascota a "Adoptada"
        psMascota = con.prepareStatement(sqlMascota);
        psMascota.setInt(1, a.getIdMascota());
        int filasMascota = psMascota.executeUpdate();

        // Verificar que ambas operaciones tuvieron éxito
        if (filasAdopcion > 0 && filasMascota > 0) {
            con.commit(); // Se confirman los cambios en la BD
            return true;
        } else {
            con.rollback(); // Se revierten las dos operaciones si algo falla
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        if (con != null) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return false;
    } finally {
        try {
            if (psAdopcion != null) psAdopcion.close();
            if (psMascota != null) psMascota.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public boolean actualizar(Adopcion a) {
        String sql = "UPDATE adopcion SET id_mascota=?, id_adoptante=?, fecha_adopcion=?, estado=?, observaciones=? "
                   + "WHERE id_adopcion=?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdMascota());
            ps.setInt(2, a.getIdAdoptante());
            ps.setDate(3, a.getFechaAdopcion());
            ps.setString(4, a.getEstado());
            ps.setString(5, a.getObservaciones());
            ps.setInt(6, a.getIdAdopcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM adopcion WHERE id_adopcion = ?";
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

    private Adopcion mapearConDetalle(ResultSet rs) throws SQLException {
        Adopcion a = new Adopcion();
        a.setIdAdopcion(rs.getInt("id_adopcion"));
        a.setIdMascota(rs.getInt("id_mascota"));
        a.setIdAdoptante(rs.getInt("id_adoptante"));
        a.setFechaAdopcion(rs.getDate("fecha_adopcion"));
        a.setEstado(rs.getString("estado"));
        a.setObservaciones(rs.getString("observaciones"));
        a.setIdUsuario(rs.getInt("id_usuario"));
        a.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        // Campos de JOIN
        a.setNombreMascota(rs.getString("nombre_mascota"));
        a.setEspecieMascota(rs.getString("especie"));
        a.setRazaMascota(rs.getString("raza"));
        a.setNombreAdoptante(rs.getString("nombre_adoptante"));
        a.setDuiAdoptante(rs.getString("dui"));
        a.setTelefonoAdoptante(rs.getString("telefono"));
        a.setRegistradoPor(rs.getString("registrado_por"));
        return a;
    }
}
