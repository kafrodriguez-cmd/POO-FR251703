<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="udb.biblioteca.PrestamoBean" %>
<%@ page import="java.util.List" %>
<%
    // Procesar devolución si se envió el parámetro
    String msgDevolucion = null;
    String idDev = request.getParameter("devolver");
    if (idDev != null && !idDev.isEmpty()) {
        try {
            PrestamoBean pDev = new PrestamoBean();
            pDev.setIdPrestamo(Integer.parseInt(idDev));
            if (pDev.devolver()) {
                msgDevolucion = "success";
            } else {
                msgDevolucion = "error";
            }
        } catch (NumberFormatException e) {
            msgDevolucion = "error";
        }
    }

    // Cargar lista de préstamos
    PrestamoBean prestamoBean = new PrestamoBean();
    List<PrestamoBean> prestamos = prestamoBean.getListaPrestamos();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Préstamos - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root { --udb-primary: #1a365d; --udb-secondary: #2b6cb0; }
        body { background: #f7fafc; min-height: 100vh; }
        .navbar { background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important; }
        .table thead { background: var(--udb-primary); color: white; }
        .badge-vigente { background-color: #38a169; }
        .badge-vencido { background-color: #e53e3e; }
        .badge-devuelto { background-color: #718096; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-book-half"></i> Biblioteca UDB</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp"><i class="bi bi-house"></i> Inicio</a>
                <a class="nav-link" href="registroLibro.jsp">Libros</a>
                <a class="nav-link" href="registroEstudiante.jsp">Estudiantes</a>
                <a class="nav-link" href="registroPrestamo.jsp">Préstamos</a>
                <a class="nav-link active" href="listaPrestamos.jsp">Listado</a>
            </div>
        </div>
    </nav>

    <div class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0"><i class="bi bi-list-ul"></i> Listado de Préstamos</h2>
            <a href="registroPrestamo.jsp" class="btn btn-warning text-dark">
                <i class="bi bi-plus-circle"></i> Nuevo Préstamo
            </a>
        </div>

        <% if ("success".equals(msgDevolucion)) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle"></i> Préstamo devuelto correctamente. La cantidad disponible del libro se ha incrementado.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } else if ("error".equals(msgDevolucion)) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle"></i> No se pudo procesar la devolución (posiblemente ya estaba devuelto).
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="card shadow border-0" style="border-radius: 1rem; overflow: hidden;">
            <div class="table-responsive">
                <table class="table table-hover table-striped mb-0">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Estudiante</th>
                            <th>Carnet</th>
                            <th>Libro</th>
                            <th>Autor</th>
                            <th>Fecha Préstamo</th>
                            <th>Fecha Devolución</th>
                            <th>Estado</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (prestamos.isEmpty()) { %>
                            <tr>
                                <td colspan="9" class="text-center text-muted py-4">
                                    No hay préstamos registrados.
                                </td>
                            </tr>
                        <% } else {
                            for (PrestamoBean p : prestamos) {
                                String estadoCalc = p.getEstadoPrestamo();
                                String badgeClass = "badge-vigente";
                                if ("Vencido".equals(estadoCalc)) badgeClass = "badge-vencido";
                                else if ("Devuelto".equals(estadoCalc)) badgeClass = "badge-devuelto";
                        %>
                            <tr>
                                <td><%= p.getIdPrestamo() %></td>
                                <td><%= p.getEstudiante().getNombreEstudiante() %></td>
                                <td><code><%= p.getEstudiante().getCarnet() %></code></td>
                                <td><%= p.getLibro().getTitulo() %></td>
                                <td><%= p.getLibro().getAutor() %></td>
                                <td><%= p.getFechaPrestamo() %></td>
                                <td><%= p.getFechaDevolucion() %></td>
                                <td>
                                    <span class="badge <%= badgeClass %>"><%= estadoCalc %></span>
                                </td>
                                <td>
                                    <% if (!"Devuelto".equals(estadoCalc)) { %>
                                        <a href="listaPrestamos.jsp?devolver=<%= p.getIdPrestamo() %>"
                                           class="btn btn-sm btn-outline-success"
                                           onclick="return confirm('¿Confirmar devolución de este préstamo?');">
                                            <i class="bi bi-arrow-return-left"></i> Devolver
                                        </a>
                                    <% } else { %>
                                        <span class="text-muted small">—</span>
                                    <% } %>
                                </td>
                            </tr>
                        <% }
                        } %>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="mt-3 text-muted small">
            <i class="bi bi-info-circle"></i>
            <strong>Vigente</strong> = fecha de devolución ≥ hoy &nbsp;|&nbsp;
            <strong>Vencido</strong> = fecha de devolución &lt; hoy &nbsp;|&nbsp;
            <strong>Devuelto</strong> = ya regresado a la biblioteca
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
