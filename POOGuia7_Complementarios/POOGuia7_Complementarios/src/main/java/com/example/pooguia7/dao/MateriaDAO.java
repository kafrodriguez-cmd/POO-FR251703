package com.example.pooguia7.dao;

import com.example.pooguia7.conexiones.Conexion;
import com.example.pooguia7.modelos.Materia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    public boolean insertar(Materia m) throws SQLException {
        String sql = "INSERT INTO materia (cod_materia, nombre, descripcion) VALUES (?,?,?)";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, m.getCodMateria());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getDescripcion());
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public boolean actualizar(Materia m) throws SQLException {
        String sql = "UPDATE materia SET nombre=?, descripcion=? WHERE cod_materia=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getDescripcion());
            ps.setInt(3, m.getCodMateria());
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public boolean eliminar(int codMateria) throws SQLException {
        String sql = "DELETE FROM materia WHERE cod_materia=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codMateria);
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public Materia buscarPorCodigo(int codMateria) throws SQLException {
        String sql = "SELECT * FROM materia WHERE cod_materia=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codMateria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Materia(
                        rs.getInt("cod_materia"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }
            return null;
        } finally {
            con.cerrarConexion();
        }
    }

    public List<Materia> listarTodos() throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materia ORDER BY cod_materia";
        Conexion con = new Conexion();
        try {
            con.setRs(sql);
            ResultSet rs = con.getRs();
            while (rs.next()) {
                lista.add(new Materia(
                        rs.getInt("cod_materia"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
        } finally {
            con.cerrarConexion();
        }
        return lista;
    }

    public boolean existe(int codMateria) throws SQLException {
        return buscarPorCodigo(codMateria) != null;
    }

    /**
     * Reporte: materias que cursa un alumno específico.
     */
    public List<Materia> materiasDeAlumno(int codAlumno) throws SQLException {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT m.cod_materia, m.nombre, m.descripcion " +
                     "FROM materia m " +
                     "INNER JOIN alumno_materia am ON m.cod_materia = am.cod_materia " +
                     "WHERE am.cod_alumno = ? " +
                     "ORDER BY m.nombre";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Materia(
                        rs.getInt("cod_materia"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }
        } finally {
            con.cerrarConexion();
        }
        return lista;
    }

    /** Asigna una materia a un alumno (relación alumno_materia). */
    public boolean asignarMateria(int codAlumno, int codMateria) throws SQLException {
        String sql = "INSERT INTO alumno_materia (cod_alumno, cod_materia) VALUES (?,?)";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codAlumno);
            ps.setInt(2, codMateria);
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }
}
