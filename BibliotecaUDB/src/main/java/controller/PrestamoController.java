package controller;

import dao.EstudianteDAO;
import dao.LibroDAO;
import dao.PrestamoDAO;
import models.EstudianteBean;
import models.LibroBean;
import models.PrestamoBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Servlet controlador para las operaciones de Préstamos.
 * Acciones: listar, nuevo, registrar, devolver
 */
@WebServlet(name = "PrestamoController", urlPatterns = {"/PrestamoController"})
public class PrestamoController extends HttpServlet {

    private PrestamoDAO prestamoDAO;
    private EstudianteDAO estudianteDAO;
    private LibroDAO libroDAO;

    @Override
    public void init() {
        prestamoDAO = new PrestamoDAO();
        estudianteDAO = new EstudianteDAO();
        libroDAO = new LibroDAO();
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
            case "devolver":
                procesarDevolucion(request, response);
                break;
            case "listar":
            default:
                listarPrestamos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("registrar".equals(action)) {
            registrarPrestamo(request, response);
        } else {
            listarPrestamos(request, response);
        }
    }

    /**
     * Muestra el formulario de préstamo con selects dinámicos.
     */
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<EstudianteBean> estudiantes = estudianteDAO.listar();
        List<LibroBean> libros = libroDAO.listarDisponibles();

        request.setAttribute("estudiantes", estudiantes);
        request.setAttribute("libros", libros);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/registroPrestamo.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Lista todos los préstamos.
     */
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PrestamoBean> prestamos = prestamoDAO.listar();
        request.setAttribute("prestamos", prestamos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaPrestamos.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Procesa el registro de un nuevo préstamo.
     */
    private void registrarPrestamo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
    int idLibro = Integer.parseInt(request.getParameter("idLibro"));
    Date fechaPrestamo = Date.valueOf(request.getParameter("fechaPrestamo"));
    Date fechaDevolucion = Date.valueOf(request.getParameter("fechaDevolucion"));

    // ===== VALIDACIÓN DE FECHAS =====
    java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());

    // La fecha de préstamo debe ser exactamente hoy
    if (!fechaPrestamo.equals(hoy)) {
        request.setAttribute("mensaje", "La fecha de préstamo debe ser la fecha de hoy.");
        request.setAttribute("tipo", "danger");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
        return;
    }

    // La fecha de devolución no puede ser anterior a la fecha de préstamo
    if (fechaDevolucion.before(fechaPrestamo)) {
        request.setAttribute("mensaje", "La fecha de devolución no puede ser anterior a la fecha de préstamo.");
        request.setAttribute("tipo", "danger");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
        return;
    }
    // ================================

    // Validar disponibilidad del libro
    if (!libroDAO.validarDisponibilidad(idLibro)) {
        request.setAttribute("mensaje", "El libro seleccionado no tiene ejemplares disponibles.");
        request.setAttribute("tipo", "danger");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
        return;
    }

    PrestamoBean prestamo = new PrestamoBean();
    prestamo.setIdEstudiante(idEstudiante);
    prestamo.setIdLibro(idLibro);
    prestamo.setFechaPrestamo(fechaPrestamo);
    prestamo.setFechaDevolucion(fechaDevolucion);
    prestamo.setEstado("Activo");

    boolean exito = prestamoDAO.registrar(prestamo);

    if (exito) {
        request.setAttribute("mensaje", "Préstamo registrado correctamente.");
        request.setAttribute("tipo", "success");
    } else {
        request.setAttribute("mensaje", "Error al registrar el préstamo.");
        request.setAttribute("tipo", "danger");
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
    dispatcher.forward(request, response);
}
    /**
     * Procesa la devolución de un préstamo.
     */
    private void procesarDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idPrestamo = Integer.parseInt(request.getParameter("id"));

        boolean exito = prestamoDAO.registrarDevolucion(idPrestamo);

        if (exito) {
            request.setAttribute("mensaje", "Devolución registrada correctamente. El libro ha vuelto a estar disponible.");
            request.setAttribute("tipo", "success");
        } else {
            request.setAttribute("mensaje", "Error al procesar la devolución.");
            request.setAttribute("tipo", "danger");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
    }
}
