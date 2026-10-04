package udb.biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean que representa un libro de la biblioteca.
 * Contiene un objeto CategoriaBean para representar la relación con categorías.
 * Métodos de negocio: getListaLibros() y getNombreCategoria().
 */
public class LibroBean {

    // Atributos privados
    private int idLibro;
    private String titulo;
    private String autor;
    private String isbn;
    private int idCategoria;
    private int cantidadDisponible;
    private CategoriaBean categoria; // Relación con CategoriaBean

    /**
     * Constructor sin argumentos
     */
    public LibroBean() {
        this.idLibro = 0;
        this.titulo = "";
        this.autor = "";
        this.isbn = "";
        this.idCategoria = 0;
        this.cantidadDisponible = 1;
        this.categoria = new CategoriaBean();
    }

    // ========== Getters y Setters ==========

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

    /**
     * Retorna el nombre de la categoría asociada al libro.
     * Si el objeto categoria no está cargado, lo consulta desde la BD.
     */
    public String getNombreCategoria() {
        if (this.categoria != null && this.categoria.getNombreCategoria() != null
                && !this.categoria.getNombreCategoria().isEmpty()) {
            return this.categoria.getNombreCategoria();
        }
        // Consulta directa si no está cargado
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String nombre = "";

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT nombre_categoria FROM categorias WHERE id_categoria = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.idCategoria);
            rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre_categoria");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre de categoría: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return nombre;
    }

    /**
     * Obtiene la lista completa de libros con su categoría.
     * @return Lista de LibroBean
     */
    public List<LibroBean> getListaLibros() {
        List<LibroBean> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT l.id_libro, l.titulo, l.autor, l.isbn, l.id_categoria, "
                       + "l.cantidad_disponible, c.nombre_categoria "
                       + "FROM libros l "
                       + "LEFT JOIN categorias c ON l.id_categoria = c.id_categoria "
                       + "ORDER BY l.titulo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LibroBean libro = new LibroBean();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setCantidadDisponible(rs.getInt("cantidad_disponible"));

                CategoriaBean cat = new CategoriaBean();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                libro.setCategoria(cat);

                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de libros: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return lista;
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     * @return true si la inserción fue exitosa
     */
    public boolean insertar() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            String sql = "INSERT INTO libros (titulo, autor, isbn, id_categoria, cantidad_disponible) "
                       + "VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.titulo);
            ps.setString(2, this.autor);
            ps.setString(3, this.isbn);
            ps.setInt(4, this.idCategoria);
            ps.setInt(5, this.cantidadDisponible);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

    /**
     * Decrementa la cantidad disponible de un libro (al prestar).
     */
    public boolean decrementarCantidad() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            String sql = "UPDATE libros SET cantidad_disponible = cantidad_disponible - 1 "
                       + "WHERE id_libro = ? AND cantidad_disponible > 0";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.idLibro);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al decrementar cantidad: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

    /**
     * Incrementa la cantidad disponible de un libro (al devolver).
     */
    public boolean incrementarCantidad() {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean exito = false;

        try {
            conn = ConexionDB.getConnection();
            String sql = "UPDATE libros SET cantidad_disponible = cantidad_disponible + 1 "
                       + "WHERE id_libro = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.idLibro);
            exito = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al incrementar cantidad: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, conn);
        }
        return exito;
    }

    /**
     * Consulta la cantidad disponible actual de un libro.
     */
    public int consultarCantidadDisponible() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cantidad = 0;

        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT cantidad_disponible FROM libros WHERE id_libro = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.idLibro);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad_disponible");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar cantidad: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, conn);
        }
        return cantidad;
    }

    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
