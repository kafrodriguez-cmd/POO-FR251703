<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Préstamos - Biblioteca UDB</title>
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
                <a class="nav-link" href="PrestamoController?action=nuevo">Nuevo Préstamo</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3><i class="bi bi-list-check"></i> Listado de Préstamos</h3>
            <a href="PrestamoController?action=nuevo" class="btn btn-warning">
                <i class="bi bi-plus-circle"></i> Nuevo Préstamo
            </a>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty prestamos}">
                        <div class="alert alert-info text-center">
                            <i class="bi bi-info-circle"></i> No hay préstamos registrados.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-hover table-striped align-middle">
                                <thead class="table-dark">
                                    <tr>
                                        <th>#</th>
                                        <th>Estudiante</th>
                                        <th>Libro</th>
                                        <th>Fecha Préstamo</th>
                                        <th>Fecha Devolución</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="p" items="${prestamos}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>
                                                <strong>${p.estudiante.carnet}</strong><br>
                                                <small class="text-muted">${p.estudiante.nombreEstudiante}</small>
                                            </td>
                                            <td>
                                                <strong>${p.libro.titulo}</strong><br>
                                                <small class="text-muted">${p.libro.autor}</small>
                                            </td>
                                            <td>${p.fechaPrestamo}</td>
                                            <td>${p.fechaDevolucion}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.estado == 'Activo'}">
                                                        <span class="badge bg-success">Activo</span>
                                                    </c:when>
                                                    <c:when test="${p.estado == 'Devuelto'}">
                                                        <span class="badge bg-secondary">Devuelto</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-warning text-dark">${p.estado}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:if test="${p.estado == 'Activo'}">
                                                    <a href="PrestamoController?action=devolver&id=${p.idPrestamo}" 
                                                       class="btn btn-sm btn-outline-primary"
                                                       onclick="return confirm('¿Confirmar devolución del libro?');">
                                                        <i class="bi bi-box-arrow-in-left"></i> Devolver
                                                    </a>
                                                </c:if>
                                                <c:if test="${p.estado == 'Devuelto'}">
                                                    <span class="text-muted small">—</span>
                                                </c:if>
                                            </td>
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
