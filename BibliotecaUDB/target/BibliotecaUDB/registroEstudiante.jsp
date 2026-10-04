<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Estudiante - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root { --udb-primary: #1a365d; --udb-secondary: #2b6cb0; }
        body { background: #f7fafc; min-height: 100vh; }
        .navbar { background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important; }
        .form-card { border: none; border-radius: 1rem; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        .form-card .card-header { background: #38a169; color: white; border-radius: 1rem 1rem 0 0 !important; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-book-half"></i> Biblioteca UDB</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp"><i class="bi bi-house"></i> Inicio</a>
                <a class="nav-link" href="registroLibro.jsp">Libros</a>
                <a class="nav-link active" href="registroEstudiante.jsp">Estudiantes</a>
                <a class="nav-link" href="registroPrestamo.jsp">Préstamos</a>
                <a class="nav-link" href="listaPrestamos.jsp">Listado</a>
            </div>
        </div>
    </nav>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card form-card">
                    <div class="card-header py-3">
                        <h4 class="mb-0"><i class="bi bi-person-plus"></i> Registrar Nuevo Estudiante</h4>
                    </div>
                    <div class="card-body p-4">
                        <form action="controllerEstudiante.jsp" method="POST">
                            <div class="mb-3">
                                <label for="carnet" class="form-label fw-semibold">Carnet *</label>
                                <input type="text" class="form-control" id="carnet" name="carnet"
                                       required maxlength="10" placeholder="Ej: AB123456"
                                       pattern="[A-Za-z0-9]{6,10}" title="6 a 10 caracteres alfanuméricos">
                            </div>

                            <div class="mb-3">
                                <label for="nombreEstudiante" class="form-label fw-semibold">Nombre Completo *</label>
                                <input type="text" class="form-control" id="nombreEstudiante" name="nombreEstudiante"
                                       required maxlength="100" placeholder="Ej: Ana María López">
                            </div>

                            <div class="mb-3">
                                <label for="carrera" class="form-label fw-semibold">Carrera *</label>
                                <select class="form-select" id="carrera" name="carrera" required>
                                    <option value="">-- Seleccione carrera --</option>
                                    <option value="Ingeniería en Computación">Ingeniería en Computación</option>
                                    <option value="Ingeniería en Sistemas">Ingeniería en Sistemas</option>
                                    <option value="Ingeniería Industrial">Ingeniería Industrial</option>
                                    <option value="Ingeniería Eléctrica">Ingeniería Eléctrica</option>
                                    <option value="Ingeniería Mecánica">Ingeniería Mecánica</option>
                                    <option value="Ingeniería Civil">Ingeniería Civil</option>
                                    <option value="Otra">Otra</option>
                                </select>
                            </div>

                            <div class="mb-4">
                                <label for="telefono" class="form-label fw-semibold">Teléfono</label>
                                <input type="text" class="form-control" id="telefono" name="telefono"
                                       maxlength="9" placeholder="Ej: 7890-1234"
                                       pattern="[0-9]{4}-?[0-9]{4}" title="Formato: 7890-1234">
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-success">
                                    <i class="bi bi-check-circle"></i> Registrar Estudiante
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
