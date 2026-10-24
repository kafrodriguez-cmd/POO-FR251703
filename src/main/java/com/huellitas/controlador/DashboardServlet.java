package com.huellitas.controlador;

import com.huellitas.dao.AdopcionDAO;
import com.huellitas.dao.MascotaDAO;
import com.huellitas.dao.AdoptanteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controlador del panel principal (dashboard).
 * Verifica sesión y muestra resumen de datos.
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
    private final AdopcionDAO adopcionDAO = new AdopcionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Contadores para el dashboard
        request.setAttribute("totalMascotas", mascotaDAO.listarTodas().size());
        request.setAttribute("mascotasDisponibles", mascotaDAO.listarDisponibles().size());
        request.setAttribute("totalAdoptantes", adoptanteDAO.listarTodos().size());
        request.setAttribute("totalAdopciones", adopcionDAO.listarTodasConDetalle().size());
        request.setAttribute("ultimasAdopciones", adopcionDAO.listarTodasConDetalle().stream().limit(5).toList());

        request.getRequestDispatcher("/WEB-INF/vistas/dashboard.jsp").forward(request, response);
    }
}
