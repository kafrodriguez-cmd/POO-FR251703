package com.huellitas.controlador;

import com.huellitas.dao.UsuarioDAO;
import com.huellitas.modelo.Usuario;
import com.huellitas.util.PasswordUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controlador de autenticación.
 * Gestiona login y logout usando HttpSession.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Si ya hay sesión activa, redirigir al dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/vistas/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwordPlana = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
            passwordPlana == null || passwordPlana.trim().isEmpty()) {
            request.setAttribute("error", "Usuario y contraseña son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/vistas/auth/login.jsp").forward(request, response);
            return;
        }

        // 1. Buscamos al usuario por su nombre de usuario
        Usuario usuario = usuarioDAO.buscarPorUsername(username.trim());

        // 2. Validamos si el usuario existe, está activo y la contraseña coincide mediante BCrypt
        boolean credencialesValidas = false;
        if (usuario != null && usuario.isActivo()) {
            credencialesValidas = PasswordUtils.checkPassword(passwordPlana, usuario.getPassword());
        }

        if (credencialesValidas) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombreCompleto());
            session.setAttribute("rol", usuario.getRol());
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("error", "Credenciales incorrectas o usuario inactivo.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/vistas/auth/login.jsp").forward(request, response);
        }
    }
}