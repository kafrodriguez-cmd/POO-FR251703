<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="conexion.jsp" %>
<%--
    Controlador de Libros.
    Recibe parámetros del formulario, los asigna al JavaBean mediante
    jsp:useBean y jsp:setProperty (property="*"), ejecuta la inserción
    y muestra confirmación con jsp:getProperty.
--%>
<jsp:useBean id="libro" class="udb.biblioteca.LibroBean" scope="request"/>
<jsp:setProperty name="libro" property="*"/>

<%
    boolean exito = false;
    String mensaje = "";

    // Solo procesar si es POST
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        exito = libro.insertar();
        if (exito) {
            mensaje = "Libro registrado exitosamente.";
        } else {
            mensaje = "Error al registrar el libro. Verifique los datos (ISBN único, categoría válida).";
        }
    } else {
        response.sendRedirect("registroLibro.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación - Libro</title>
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
                            <h3 class="text-success"><%= mensaje %></h3>
                            <hr>
                            <div class="text-start mt-3">
                                <h5><i class="bi bi-info-circle"></i> Datos registrados:</h5>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Título:</strong>
                                        <span><jsp:getProperty name="libro" property="titulo"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Autor:</strong>
                                        <span><jsp:getProperty name="libro" property="autor"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>ISBN:</strong>
                                        <span><jsp:getProperty name="libro" property="isbn"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>ID Categoría:</strong>
                                        <span><jsp:getProperty name="libro" property="idCategoria"/></span>
                                    </li>
                                    <li class="list-group-item d-flex justify-content-between">
                                        <strong>Cantidad:</strong>
                                        <span><jsp:getProperty name="libro" property="cantidadDisponible"/></span>
                                    </li>
                                </ul>
                            </div>
                        <% } else { %>
                            <div class="mb-3">
                                <i class="bi bi-x-circle-fill text-danger" style="font-size: 4rem;"></i>
                            </div>
                            <h3 class="text-danger"><%= mensaje %></h3>
                        <% } %>

                        <div class="mt-4 d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="registroLibro.jsp" class="btn btn-primary">
                                <i class="bi bi-plus-circle"></i> Registrar otro
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
