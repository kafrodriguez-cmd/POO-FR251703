package com.huellitas.controlador;

import com.huellitas.dao.UsuarioDAO;
import com.huellitas.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Controlador CRUD de Trabajadores (Usuarios del sistema).
 * Solo accesible para rol ADMIN.
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/trabajadores"})
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;
        if (!esAdmin(request, response)) return;

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo" -> {
                request.setAttribute("usuarioEdit", null);
                request.getRequestDispatcher("/WEB-INF/vistas/usuarios/formulario.jsp").forward(request, response);
            }
            case "editar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("usuarioEdit", dao.buscarPorId(id));
                request.getRequestDispatcher("/WEB-INF/vistas/usuarios/formulario.jsp").forward(request, response);
            }
            case "eliminar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                // Evitar que el admin se elimine a sí mismo
                Usuario actual = (Usuario) request.getSession().getAttribute("usuario");
                if (actual.getIdUsuario() == id) {
                    request.getSession().setAttribute("error", "No puede eliminarse a usted mismo.");
                } else {
                    boolean ok = dao.eliminar(id);
                    request.getSession().setAttribute(ok ? "mensaje" : "error",
                            ok ? "Trabajador eliminado correctamente." : "No se pudo eliminar.");
                }
                response.sendRedirect(request.getContextPath() + "/trabajadores");
            }
            default -> {
                List<Usuario> lista = dao.listarTodos();
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("/WEB-INF/vistas/usuarios/lista.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) return;
        if (!esAdmin(request, response)) return;

        String idStr = request.getParameter("idUsuario");
        Usuario u = new Usuario();
        if (idStr != null && !idStr.isEmpty()) {
            u.setIdUsuario(Integer.parseInt(idStr));
        }

        u.setUsername(request.getParameter("username"));
        u.setPassword(request.getParameter("password"));
        u.setNombreCompleto(request.getParameter("nombreCompleto"));
        u.setRol(request.getParameter("rol"));
        u.setActivo("on".equals(request.getParameter("activo")) || "true".equals(request.getParameter("activo")));

        // Validaciones básicas
        if (u.getUsername() == null || u.getUsername().isBlank() ||
            u.getNombreCompleto() == null || u.getNombreCompleto().isBlank()) {
            request.setAttribute("error", "Usuario y nombre completo son obligatorios.");
            request.setAttribute("usuarioEdit", u);
            request.getRequestDispatcher("/WEB-INF/vistas/usuarios/formulario.jsp").forward(request, response);
            return;
        }

        boolean ok;
        if (u.getIdUsuario() > 0) {
            // Si no se envió password nueva, no la cambiamos (el DAO actual no soporta update parcial de pass)
            ok = dao.actualizar(u);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Trabajador actualizado." : "Error al actualizar.");
        } else {
            if (u.getPassword() == null || u.getPassword().isBlank()) {
                request.setAttribute("error", "La contraseña es obligatoria para nuevos trabajadores.");
                request.setAttribute("usuarioEdit", u);
                request.getRequestDispatcher("/WEB-INF/vistas/usuarios/formulario.jsp").forward(request, response);
                return;
            }
            ok = dao.insertar(u);
            request.getSession().setAttribute(ok ? "mensaje" : "error",
                    ok ? "Trabajador registrado correctamente." : "Error al registrar (usuario duplicado?).");
        }

        response.sendRedirect(request.getContextPath() + "/trabajadores");
    }

    private boolean haySesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    private boolean esAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        if (u == null || !u.esAdmin()) {
            request.getSession().setAttribute("error", "Acceso denegado. Solo administradores.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return false;
        }
        return true;
    }
}
