<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Estudiantes - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #1e3a5f;">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">
                <i class="bi bi-book-half"></i> Biblioteca UDB
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp">Inicio</a>
                <a class="nav-link" href="EstudianteController?action=nuevo">Registrar Estudiante</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3><i class="bi bi-people"></i> Listado de Estudiantes</h3>
            <a href="EstudianteController?action=nuevo" class="btn btn-success">
                <i class="bi bi-person-plus"></i> Nuevo Estudiante
            </a>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty estudiantes}">
                        <div class="alert alert-info text-center">
                            No hay estudiantes registrados.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-hover table-striped align-middle">
                                <thead class="table-dark">
                                    <tr>
                                        <th>#</th>
                                        <th>Carné</th>
                                        <th>Nombre</th>
                                        <th>Carrera</th>
                                        <th>Teléfono</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="est" items="${estudiantes}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td><code>${est.carnet}</code></td>
                                            <td><strong>${est.nombreEstudiante}</strong></td>
                                            <td>${est.carrera}</td>
                                            <td>${est.telefono}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
