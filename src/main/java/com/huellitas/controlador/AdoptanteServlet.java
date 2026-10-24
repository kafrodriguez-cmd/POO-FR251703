package com.huellitas.controlador;

import com.huellitas.dao.AdoptanteDAO;
import com.huellitas.modelo.Adoptante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdoptanteServlet", urlPatterns = {"/adoptantes"})
public class AdoptanteServlet extends HttpServlet {

    private final AdoptanteDAO dao = new AdoptanteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo" -> mostrarFormulario(request, response, null);
            case "editar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                mostrarFormulario(request, response, dao.buscarPorId(id));
            }
            case "eliminar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminar(id);
                request.getSession().setAttribute(ok ? "mensaje" : "error",
                        ok ? "Adoptante eliminado correctamente." : "No se pudo eliminar (puede tener adopciones asociadas).");
                response.sendRedirect(request.getContextPath() + "/adoptantes");
            }
            case "buscar" -> {
                String criterio = request.getParameter("criterio");
                List<Adoptante> resultados = dao.buscarPorNombre(criterio);
                request.setAttribute("lista", resultados);
                request.setAttribute("criterio", criterio);
                request.getRequestDispatcher("/WEB-INF/vistas/adoptantes/lista.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute("lista", dao.listarTodos());
                request.getRequestDispatcher("/WEB-INF/vistas/adoptantes/lista.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;

        String idStr = request.getParameter("idAdoptante");
        Adoptante a = new Adoptante();
        if (idStr != null && !idStr.isEmpty()) {
            a.setIdAdoptante(Integer.parseInt(idStr));
        }

        a.setNombres(request.getParameter("nombres"));
        a.setApellidos(request.getParameter("apellidos"));
        a.setDui(request.getParameter("dui"));
        a.setTelefono(request.getParameter("telefono"));
        a.setEmail(request.getParameter("email"));
        a.setDireccion(request.getParameter("direccion"));

        if (a.getNombres() == null || a.getNombres().trim().isEmpty() ||
            a.getApellidos() == null || a.getApellidos().trim().isEmpty() ||
            a.getDui() == null || a.getDui().trim().isEmpty()) {
            request.setAttribute("error", "Nombres, apellidos y DUI son campos obligatorios.");
            mostrarFormulario(request, response, a);
            return;
        }

        boolean ok;
        if (a.getIdAdoptante() > 0) {
            ok = dao.actualizar(a);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Adoptante actualizado correctamente." : "Error al actualizar.");
        } else {
            ok = dao.insertar(a);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Adoptante registrado correctamente." : "Error al registrar (DUI duplicado?).");
        }

        response.sendRedirect(request.getContextPath() + "/adoptantes");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Adoptante a)
            throws ServletException, IOException {
        request.setAttribute("adoptante", a);
        request.getRequestDispatcher("/WEB-INF/vistas/adoptantes/formulario.jsp").forward(request, response);
    }

    private boolean haySesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}