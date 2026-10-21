<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Libro - Biblioteca UDB</title>
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
                <a class="nav-link" href="LibroController?action=listar">Listar Libros</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0"><i class="bi bi-journal-plus"></i> Registrar Nuevo Libro</h4>
                    </div>
                    <div class="card-body">
                        <form action="LibroController" method="post">
                            <input type="hidden" name="action" value="registrar">

                            <div class="mb-3">
                                <label for="titulo" class="form-label">Título <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="titulo" name="titulo" 
                                       required maxlength="150" placeholder="Ej: Clean Code">
                            </div>

                            <div class="mb-3">
                                <label for="autor" class="form-label">Autor <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="autor" name="autor" 
                                       required maxlength="100" placeholder="Ej: Robert C. Martin">
                            </div>

                            <div class="mb-3">
                                <label for="isbn" class="form-label">ISBN</label>
                                <input type="text" class="form-control" id="isbn" name="isbn" 
                                       maxlength="20" placeholder="Ej: 978-0132350884">
                            </div>

                            <div class="mb-3">
                                <label for="idCategoria" class="form-label">Categoría <span class="text-danger">*</span></label>
                                <select class="form-select" id="idCategoria" name="idCategoria" required>
                                    <option value="">-- Seleccione una categoría --</option>
                                    <c:forEach var="cat" items="${categorias}">
                                        <option value="${cat.idCategoria}">${cat.nombreCategoria}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="cantidadDisponible" class="form-label">Cantidad Disponible <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="cantidadDisponible" 
                                       name="cantidadDisponible" required min="1" value="1">
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Guardar Libro
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
