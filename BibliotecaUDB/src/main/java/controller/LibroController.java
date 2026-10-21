package controller;

import dao.CategoriaDAO;
import dao.LibroDAO;
import models.CategoriaBean;
import models.LibroBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para las operaciones de Libros.
 * Acciones: listar, mostrarFormulario, registrar
 */
@WebServlet(name = "LibroController", urlPatterns = {"/LibroController"})
public class LibroController extends HttpServlet {

    private LibroDAO libroDAO;
    private CategoriaDAO categoriaDAO;

    @Override
    public void init() {
        libroDAO = new LibroDAO();
        categoriaDAO = new CategoriaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "nuevo":
                mostrarFormulario(request, response);
                break;
            case "listar":
            default:
                listarLibros(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("registrar".equals(action)) {
            registrarLibro(request, response);
        } else {
            listarLibros(request, response);
        }
    }

    /**
     * Muestra el formulario de registro de libro con el select de categorías.
     */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CategoriaBean> categorias = categoriaDAO.listar();
        request.setAttribute("categorias", categorias);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/registroLibro.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Lista todos los libros.
     */
    private void listarLibros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LibroBean> libros = libroDAO.listar();
        request.setAttribute("libros", libros);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaLibros.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Procesa el registro de un nuevo libro.
     */
    private void registrarLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recibir parámetros del formulario
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        int cantidad = Integer.parseInt(request.getParameter("cantidadDisponible"));

        // Crear JavaBean
        LibroBean libro = new LibroBean();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);
        libro.setIdCategoria(idCategoria);
        libro.setCantidadDisponible(cantidad);

        // Invocar DAO
        boolean exito = libroDAO.registrar(libro);

        if (exito) {
            request.setAttribute("mensaje", "Libro registrado correctamente: " + titulo);
            request.setAttribute("tipo", "success");
        } else {
            request.setAttribute("mensaje", "Error al registrar el libro. Verifique los datos (ISBN único).");
            request.setAttribute("tipo", "danger");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
    }
}
