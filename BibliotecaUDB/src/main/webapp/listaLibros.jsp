<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de Libros - Biblioteca UDB</title>
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
                <a class="nav-link" href="LibroController?action=nuevo">Registrar Libro</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3><i class="bi bi-journal-bookmark"></i> Catálogo de Libros</h3>
            <a href="LibroController?action=nuevo" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nuevo Libro
            </a>
        </div>

        <div class="card shadow">
            <div class="card-body">
                <c:choose>
                    <c:when test="${empty libros}">
                        <div class="alert alert-info text-center">
                            No hay libros registrados.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-hover table-striped align-middle">
                                <thead class="table-dark">
                                    <tr>
                                        <th>#</th>
                                        <th>Título</th>
                                        <th>Autor</th>
                                        <th>ISBN</th>
                                        <th>Categoría</th>
                                        <th>Disponibles</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="lib" items="${libros}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td><strong>${lib.titulo}</strong></td>
                                            <td>${lib.autor}</td>
                                            <td><code>${lib.isbn}</code></td>
                                            <td>
                                                <span class="badge bg-info text-dark">
                                                    ${lib.categoria.nombreCategoria}
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${lib.cantidadDisponible > 0}">
                                                        <span class="badge bg-success">${lib.cantidadDisponible}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger">0</span>
                                                    </c:otherwise>
                                                </c:choose>
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
