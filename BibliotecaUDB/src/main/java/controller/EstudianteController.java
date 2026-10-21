package controller;

import dao.EstudianteDAO;
import models.EstudianteBean;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet controlador para las operaciones de Estudiantes.
 */
@WebServlet(name = "EstudianteController", urlPatterns = {"/EstudianteController"})
public class EstudianteController extends HttpServlet {

    private EstudianteDAO estudianteDAO;

    @Override
    public void init() {
        estudianteDAO = new EstudianteDAO();
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
                listarEstudiantes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("registrar".equals(action)) {
            registrarEstudiante(request, response);
        } else {
            listarEstudiantes(request, response);
        }
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/registroEstudiante.jsp");
        dispatcher.forward(request, response);
    }

    private void listarEstudiantes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<EstudianteBean> estudiantes = estudianteDAO.listar();
        request.setAttribute("estudiantes", estudiantes);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/listaEstudiantes.jsp");
        dispatcher.forward(request, response);
    }

    private void registrarEstudiante(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carnet = request.getParameter("carnet");
        String nombre = request.getParameter("nombreEstudiante");
        String carrera = request.getParameter("carrera");
        String telefono = request.getParameter("telefono");

        EstudianteBean estudiante = new EstudianteBean();
        estudiante.setCarnet(carnet);
        estudiante.setNombreEstudiante(nombre);
        estudiante.setCarrera(carrera);
        estudiante.setTelefono(telefono);

        boolean exito = estudianteDAO.registrar(estudiante);

        if (exito) {
            request.setAttribute("mensaje", "Estudiante registrado correctamente: " + nombre);
            request.setAttribute("tipo", "success");
        } else {
            request.setAttribute("mensaje", "Error al registrar el estudiante. Verifique que el carnet sea único.");
            request.setAttribute("tipo", "danger");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmacion.jsp");
        dispatcher.forward(request, response);
    }
}
