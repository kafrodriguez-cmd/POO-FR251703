package com.huellitas.controlador;

import com.huellitas.dao.AdopcionDAO;
import com.huellitas.dao.AdoptanteDAO;
import com.huellitas.dao.MascotaDAO;
import com.huellitas.modelo.Adopcion;
import com.huellitas.modelo.Usuario;
import com.huellitas.util.FechaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(name = "AdopcionServlet", urlPatterns = {"/adopciones", "/reportes/adopciones"})
public class AdopcionServlet extends HttpServlet {

    private final AdopcionDAO dao = new AdopcionDAO();
    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;

        String path = request.getServletPath();
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        // Reporte con JOIN
        if ("/reportes/adopciones".equals(path)) {
            request.setAttribute("lista", dao.listarTodasConDetalle());
            request.getRequestDispatcher("/WEB-INF/vistas/reportes/adopciones.jsp").forward(request, response);
            return;
        }

        switch (accion) {
            case "nuevo" -> {
                request.setAttribute("mascotas", mascotaDAO.listarDisponibles());
                request.setAttribute("adoptantes", adoptanteDAO.listarTodos());
                request.getRequestDispatcher("/WEB-INF/vistas/adopciones/formulario.jsp").forward(request, response);
            }
            case "editar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                Adopcion a = dao.buscarPorId(id);
                request.setAttribute("adopcion", a);
                request.setAttribute("mascotas", mascotaDAO.listarTodas());
                request.setAttribute("adoptantes", adoptanteDAO.listarTodos());
                request.getRequestDispatcher("/WEB-INF/vistas/adopciones/formulario.jsp").forward(request, response);
            }
            case "eliminar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminar(id);
                request.getSession().setAttribute(ok ? "mensaje" : "error",
                        ok ? "Adopción eliminada." : "No se pudo eliminar.");
                response.sendRedirect(request.getContextPath() + "/adopciones");
            }
            default -> {
                request.setAttribute("lista", dao.listarTodasConDetalle());
                request.getRequestDispatcher("/WEB-INF/vistas/adopciones/lista.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String idStr = request.getParameter("idAdopcion");
        Adopcion a = new Adopcion();
        if (idStr != null && !idStr.isEmpty()) {
            a.setIdAdopcion(Integer.parseInt(idStr));
        }

        a.setIdMascota(Integer.parseInt(request.getParameter("idMascota")));
        a.setIdAdoptante(Integer.parseInt(request.getParameter("idAdoptante")));

        // Validación de fecha de adopción: solo hoy o fechas futuras (dentro del año actual)
        LocalDate fechaAdop = FechaUtil.parse(request.getParameter("fechaAdopcion"));
        String errorFecha = FechaUtil.validarFechaFutura(fechaAdop, "fecha de adopción / cita");
        if (errorFecha != null) {
            request.setAttribute("error", errorFecha);
            request.setAttribute("adopcion", a);
            request.setAttribute("mascotas", mascotaDAO.listarDisponibles());
            request.setAttribute("adoptantes", adoptanteDAO.listarTodos());
            request.getRequestDispatcher("/WEB-INF/vistas/adopciones/formulario.jsp").forward(request, response);
            return;
        }
        a.setFechaAdopcion(Date.valueOf(fechaAdop));

        a.setEstado(request.getParameter("estado"));
        a.setObservaciones(request.getParameter("observaciones"));
        a.setIdUsuario(usuario.getIdUsuario());

        boolean ok;
        if (a.getIdAdopcion() > 0) {
            ok = dao.actualizar(a);
        } else {
            ok = dao.insertar(a);
            // Si se registra como Finalizada o Aprobada, actualizar estado de la mascota
            if (ok && ("Finalizada".equals(a.getEstado()) || "Aprobada".equals(a.getEstado()))) {
                mascotaDAO.actualizarEstado(a.getIdMascota(), "Adoptada");
            } else if (ok && "Pendiente".equals(a.getEstado())) {
                mascotaDAO.actualizarEstado(a.getIdMascota(), "En proceso");
            }
        }

        session.setAttribute(ok ? "mensaje" : "error",
                ok ? "Adopción guardada correctamente." : "Error al guardar la adopción.");
        response.sendRedirect(request.getContextPath() + "/adopciones");
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
