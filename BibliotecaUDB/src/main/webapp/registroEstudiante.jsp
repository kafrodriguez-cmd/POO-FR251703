<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Estudiante - Biblioteca UDB</title>
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
                <a class="nav-link" href="EstudianteController?action=listar">Listar Estudiantes</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-success text-white">
                        <h4 class="mb-0"><i class="bi bi-person-plus"></i> Registrar Nuevo Estudiante</h4>
                    </div>
                    <div class="card-body">
                        <form action="EstudianteController" method="post">
                            <input type="hidden" name="action" value="registrar">

                            <div class="mb-3">
                                <label for="carnet" class="form-label">Carné <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="carnet" name="carnet" 
                                       required maxlength="10" placeholder="Ej: GB25001">
                            </div>

                            <div class="mb-3">
                                <label for="nombreEstudiante" class="form-label">Nombre Completo <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="nombreEstudiante" name="nombreEstudiante" 
                                       required maxlength="100" placeholder="Ej: Ana María López">
                            </div>

                            <div class="mb-3">
                                <label for="carrera" class="form-label">Carrera <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="carrera" name="carrera" 
                                       required maxlength="80" placeholder="Ej: Ingeniería en Computación">
                            </div>

                            <div class="mb-3">
                                <label for="telefono" class="form-label">Teléfono</label>
                                <input type="text" class="form-control" id="telefono" name="telefono" 
                                       maxlength="9" placeholder="Ej: 7854-1234">
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-success">
                                    <i class="bi bi-save"></i> Guardar Estudiante
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
