package com.huellitas.dao;

import com.huellitas.modelo.Mascota;
import com.huellitas.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Mascota.
 * Incluye CRUD completo + búsqueda/filtrado.
 */
public class MascotaDAO {

    public List<Mascota> listarTodas() {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota ORDER BY fecha_ingreso DESC";
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

    /** Lista solo las mascotas disponibles para adopción */
    public List<Mascota> listarDisponibles() {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota WHERE estado = 'Disponible' ORDER BY nombre";
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

    /**
     * Busca / filtra mascotas por nombre, especie o estado.
     */
    public List<Mascota> buscar(String criterio, String especie, String estado) {
        List<Mascota> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM mascota WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criterio != null && !criterio.trim().isEmpty()) {
            sql.append(" AND (nombre LIKE ? OR raza LIKE ? OR color LIKE ?)");
            String like = "%" + criterio.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }
        if (especie != null && !especie.trim().isEmpty() && !"Todas".equals(especie)) {
            sql.append(" AND especie = ?");
            params.add(especie);
        }
        if (estado != null && !estado.trim().isEmpty() && !"Todos".equals(estado)) {
            sql.append(" AND estado = ?");
            params.add(estado);
        }
        sql.append(" ORDER BY nombre");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
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

    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascota WHERE id_mascota = ?";
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

    public boolean insertar(Mascota m) {
        String sql = "INSERT INTO mascota (nombre, especie, raza, edad_aprox, sexo, color, tamano, estado, descripcion, fecha_ingreso, foto_url) "
                   + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setString(3, m.getRaza());
            ps.setInt(4, m.getEdadAprox());
            ps.setString(5, m.getSexo());
            ps.setString(6, m.getColor());
            ps.setString(7, m.getTamano());
            ps.setString(8, m.getEstado() != null ? m.getEstado() : "Disponible");
            ps.setString(9, m.getDescripcion());
            ps.setDate(10, m.getFechaIngreso());
            ps.setString(11, m.getFotoUrl() != null ? m.getFotoUrl() : "default_mascota.png");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    public boolean actualizar(Mascota m) {
        String sql = "UPDATE mascota SET nombre=?, especie=?, raza=?, edad_aprox=?, sexo=?, color=?, "
                   + "tamano=?, estado=?, descripcion=?, fecha_ingreso=?, foto_url=? WHERE id_mascota=?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setString(3, m.getRaza());
            ps.setInt(4, m.getEdadAprox());
            ps.setString(5, m.getSexo());
            ps.setString(6, m.getColor());
            ps.setString(7, m.getTamano());
            ps.setString(8, m.getEstado());
            ps.setString(9, m.getDescripcion());
            ps.setDate(10, m.getFechaIngreso());
            ps.setString(11, m.getFotoUrl());
            ps.setInt(12, m.getIdMascota());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascota WHERE id_mascota = ?";
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

    /** Actualiza solo el estado de una mascota (útil al aprobar/finalizar adopción) */
    public boolean actualizarEstado(int idMascota, String nuevoEstado) {
        String sql = "UPDATE mascota SET estado = ? WHERE id_mascota = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMascota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Conexion.close(ps);
        }
    }

    private Mascota mapear(ResultSet rs) throws SQLException {
        Mascota m = new Mascota();
        m.setIdMascota(rs.getInt("id_mascota"));
        m.setNombre(rs.getString("nombre"));
        m.setEspecie(rs.getString("especie"));
        m.setRaza(rs.getString("raza"));
        m.setEdadAprox(rs.getInt("edad_aprox"));
        m.setSexo(rs.getString("sexo"));
        m.setColor(rs.getString("color"));
        m.setTamano(rs.getString("tamano"));
        m.setEstado(rs.getString("estado"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setFechaIngreso(rs.getDate("fecha_ingreso"));
        m.setFotoUrl(rs.getString("foto_url"));
        return m;
    }
}
