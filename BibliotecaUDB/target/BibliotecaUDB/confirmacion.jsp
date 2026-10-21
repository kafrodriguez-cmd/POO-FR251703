<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #1e3a5f;">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">
                <i class="bi bi-book-half"></i> Biblioteca UDB
            </a>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow text-center">
                    <div class="card-body p-5">
                        <c:choose>
                            <c:when test="${tipo == 'success'}">
                                <div class="text-success mb-3">
                                    <i class="bi bi-check-circle-fill" style="font-size: 4rem;"></i>
                                </div>
                                <h3 class="text-success">¡Éxito!</h3>
                            </c:when>
                            <c:otherwise>
                                <div class="text-danger mb-3">
                                    <i class="bi bi-x-circle-fill" style="font-size: 4rem;"></i>
                                </div>
                                <h3 class="text-danger">Error</h3>
                            </c:otherwise>
                        </c:choose>

                        <p class="lead mt-3">${mensaje}</p>

                        <div class="d-grid gap-2 mt-4">
                            <a href="index.jsp" class="btn btn-primary">
                                <i class="bi bi-house"></i> Volver al Inicio
                            </a>
                            <a href="PrestamoController?action=listar" class="btn btn-outline-secondary">
                                Ver Préstamos
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
