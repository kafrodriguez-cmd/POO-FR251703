package com.example.pooguia7.dao;

import com.example.pooguia7.conexiones.Conexion;
import com.example.pooguia7.modelos.Alumno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public boolean insertar(Alumno a) throws SQLException {
        String sql = "INSERT INTO alumno (cod_alumno, nombre, apellido, edad, direccion) VALUES (?,?,?,?,?)";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, a.getCodAlumno());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setInt(4, a.getEdad());
            ps.setString(5, a.getDireccion());
            int filas = ps.executeUpdate();
            return filas > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public boolean actualizar(Alumno a) throws SQLException {
        String sql = "UPDATE alumno SET nombre=?, apellido=?, edad=?, direccion=? WHERE cod_alumno=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setInt(3, a.getEdad());
            ps.setString(4, a.getDireccion());
            ps.setInt(5, a.getCodAlumno());
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public boolean eliminar(int codAlumno) throws SQLException {
        String sql = "DELETE FROM alumno WHERE cod_alumno=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codAlumno);
            return ps.executeUpdate() > 0;
        } finally {
            con.cerrarConexion();
        }
    }

    public Alumno buscarPorCodigo(int codAlumno) throws SQLException {
        String sql = "SELECT * FROM alumno WHERE cod_alumno=?";
        Conexion con = new Conexion();
        try (PreparedStatement ps = con.getConnection().prepareStatement(sql)) {
            ps.setInt(1, codAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Alumno(
                        rs.getInt("cod_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("direccion")
                );
            }
            return null;
        } finally {
            con.cerrarConexion();
        }
    }

    public List<Alumno> listarTodos() throws SQLException {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno ORDER BY cod_alumno";
        Conexion con = new Conexion();
        try {
            con.setRs(sql);
            ResultSet rs = con.getRs();
            while (rs.next()) {
                lista.add(new Alumno(
                        rs.getInt("cod_alumno"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("direccion")
                ));
            }
        } finally {
            con.cerrarConexion();
        }
        return lista;
    }

    public boolean existe(int codAlumno) throws SQLException {
        return buscarPorCodigo(codAlumno) != null;
    }
}
