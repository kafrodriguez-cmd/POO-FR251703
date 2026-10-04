<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="conexion.jsp" %>
<%--
    Controlador de Préstamos.
    Usa jsp:useBean + jsp:setProperty + jsp:getProperty.
    Las fechas se asignan de forma segura (String → java.sql.Date)
    porque property="*" no convierte bien el tipo DATE del formulario HTML.
--%>
<jsp:useBean id="prestamo" class="udb.biblioteca.PrestamoBean" scope="request"/>

<%-- Asignación de propiedades simples (coinciden con name del formulario) --%>
<jsp:setProperty name="prestamo" property="idEstudiante"/>
<jsp:setProperty name="prestamo" property="idLibro"/>
<jsp:setProperty name="prestamo" property="estado"/>

<%
    // Conversión segura de fechas (input type="date" envía "yyyy-MM-dd")
    String fp = request.getParameter("fechaPrestamo");
    String fd = request.getParameter("fechaDevolucion");
    if (fp != null && !fp.trim().isEmpty()) {
        prestamo.setFechaPrestamo(fp.trim());
    }
    if (fd != null && !fd.trim().isEmpty()) {
        prestamo.setFechaDevolucion(fd.trim());
    }

    String resultado = "";
    boolean exito = false;

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        resultado = prestamo.insertar();
        exito = "OK".equals(resultado);
        if (exito) {
            resultado = "Préstamo registrado exitosamente.";
        }
    } else {
        response.sendRedirect("registroPrestamo.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación - Préstamo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root { --udb-primary: #1a365d; --udb-secondary: #2b6cb0; }
        body { background: #f7fafc; min-height: 100vh; }
        .navbar { background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-book-half"></i> Biblioteca UDB</a>
        </div>
    </nav>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow border-0" style="border-radius: 1rem;">
                    <div class="card-body p-4 text-center">
                        <% if (exito) { %>
                            <div class="mb-3">
                                <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
                            </div>
                            <h3 class="text-success"><%= resultado %></h3>
                            <hr>
                            <div class="text-start mt-3">
                                <h5><i class="bi bi-info-circle"></i> Datos del préstamo:</h5>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>ID Estudiante:</strong>
                                        <span><jsp:getProperty name="prestamo" property="idEstudiante"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>ID Libro:</strong>
                                        <span><jsp:getProperty name="prestamo" property="idLibro"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Fecha Préstamo:</strong>
                                        <span><jsp:getProperty name="prestamo" property="fechaPrestamo"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Fecha Devolución:</strong>
                                        <span><jsp:getProperty name="prestamo" property="fechaDevolucion"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Estado:</strong>
                                        <span class="badge bg-success"><jsp:getProperty name="prestamo" property="estado"/></span>
                                    </li>
                                </ul>
                            </div>
                        <% } else { %>
                            <div class="mb-3">
                                <i class="bi bi-x-circle-fill text-danger" style="font-size: 4rem;"></i>
                            </div>
                            <h3 class="text-danger"><%= resultado %></h3>
                            <p class="text-muted mt-2">No se realizó el préstamo. Verifique la disponibilidad del libro o la conexión a MySQL.</p>
                        <% } %>

                        <div class="mt-4 d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="registroPrestamo.jsp" class="btn btn-warning text-dark">
                                <i class="bi bi-plus-circle"></i> Registrar otro
                            </a>
                            <a href="listaPrestamos.jsp" class="btn btn-outline-primary">
                                <i class="bi bi-list-ul"></i> Ver listado
                            </a>
                            <a href="index.jsp" class="btn btn-outline-secondary">
                                <i class="bi bi-house"></i> Inicio
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
