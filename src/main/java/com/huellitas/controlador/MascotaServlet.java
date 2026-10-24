package com.huellitas.controlador;

import com.huellitas.dao.MascotaDAO;
import com.huellitas.modelo.Mascota;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.huellitas.util.FechaUtil;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador CRUD completo de Mascotas.
 * Aplica reglas de fecha: la fecha de ingreso/rescate no puede ser futura.
 */
@WebServlet(name = "MascotaServlet", urlPatterns = {"/mascotas"})
public class MascotaServlet extends HttpServlet {

    private final MascotaDAO dao = new MascotaDAO();

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
                Mascota m = dao.buscarPorId(id);
                mostrarFormulario(request, response, m);
            }
            case "eliminar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean ok = dao.eliminar(id);
                request.getSession().setAttribute(ok ? "mensaje" : "error",
                        ok ? "Mascota eliminada correctamente." : "No se pudo eliminar la mascota.");
                response.sendRedirect(request.getContextPath() + "/mascotas");
            }
            case "buscar" -> {
                String criterio = request.getParameter("criterio");
                String especie  = request.getParameter("especie");
                String estado   = request.getParameter("estado");
                List<Mascota> resultados = dao.buscar(criterio, especie, estado);
                request.setAttribute("lista", resultados);
                request.setAttribute("criterio", criterio);
                request.setAttribute("especieFiltro", especie);
                request.setAttribute("estadoFiltro", estado);
                request.getRequestDispatcher("/WEB-INF/vistas/mascotas/lista.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute("lista", dao.listarTodas());
                request.getRequestDispatcher("/WEB-INF/vistas/mascotas/lista.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;

        String idStr = request.getParameter("idMascota");
        Mascota m = new Mascota();

        if (idStr != null && !idStr.isEmpty()) {
            m.setIdMascota(Integer.parseInt(idStr));
        }

        m.setNombre(request.getParameter("nombre"));
        m.setEspecie(request.getParameter("especie"));
        m.setRaza(request.getParameter("raza"));
        m.setEdadAprox(parseIntSafe(request.getParameter("edadAprox")));
        m.setSexo(request.getParameter("sexo"));
        m.setColor(request.getParameter("color"));
        m.setTamano(request.getParameter("tamano"));
        m.setEstado(request.getParameter("estado"));
        m.setDescripcion(request.getParameter("descripcion"));
        m.setFotoUrl(request.getParameter("fotoUrl"));

        String fechaStr = request.getParameter("fechaIngreso");
        LocalDate fechaIngreso = FechaUtil.parse(fechaStr);
        String errorFecha = FechaUtil.validarFechaIngreso(fechaIngreso);
        if (errorFecha != null) {
            request.setAttribute("error", errorFecha);
            mostrarFormulario(request, response, m);
            return;
        }
        m.setFechaIngreso(Date.valueOf(fechaIngreso));

        // Validación básica
        if (m.getNombre() == null || m.getNombre().trim().isEmpty()) {
            request.setAttribute("error", "El nombre de la mascota es obligatorio.");
            mostrarFormulario(request, response, m);
            return;
        }

        boolean ok;
        if (m.getIdMascota() > 0) {
            ok = dao.actualizar(m);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Mascota actualizada correctamente." : "Error al actualizar.");
        } else {
            ok = dao.insertar(m);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Mascota registrada correctamente." : "Error al registrar.");
        }

        response.sendRedirect(request.getContextPath() + "/mascotas");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Mascota m)
            throws ServletException, IOException {
        request.setAttribute("mascota", m);
        request.getRequestDispatcher("/WEB-INF/vistas/mascotas/formulario.jsp").forward(request, response);
    }

    private boolean haySesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    private int parseIntSafe(String val) {
        try { return Integer.parseInt(val); } catch (Exception e) { return 0; }
    }
}
