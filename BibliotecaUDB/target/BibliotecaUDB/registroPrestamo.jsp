<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Préstamo - Biblioteca UDB</title>
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
                <a class="nav-link" href="PrestamoController?action=listar">Listar Préstamos</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-warning text-dark">
                        <h4 class="mb-0"><i class="bi bi-arrow-left-right"></i> Registrar Nuevo Préstamo</h4>
                    </div>
                    <div class="card-body">
                        <form action="PrestamoController" method="post">
                            <input type="hidden" name="action" value="registrar">

                            <div class="mb-3">
                                <label for="idEstudiante" class="form-label">Estudiante <span class="text-danger">*</span></label>
                                <select class="form-select" id="idEstudiante" name="idEstudiante" required>
                                    <option value="">-- Seleccione un estudiante --</option>
                                    <c:forEach var="est" items="${estudiantes}">
                                        <option value="${est.idEstudiante}">
                                            ${est.carnet} - ${est.nombreEstudiante} (${est.carrera})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="idLibro" class="form-label">Libro <span class="text-danger">*</span></label>
                                <select class="form-select" id="idLibro" name="idLibro" required>
                                    <option value="">-- Seleccione un libro disponible --</option>
                                    <c:forEach var="lib" items="${libros}">
                                        <option value="${lib.idLibro}">
                                            ${lib.titulo} - ${lib.autor} (Disp: ${lib.cantidadDisponible})
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="form-text">Solo se muestran libros con ejemplares disponibles.</div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="fechaPrestamo" class="form-label">Fecha de Préstamo <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="fechaPrestamo" name="fechaPrestamo" 
                                           required readonly>
                                    <div class="form-text text-muted">Se asigna automáticamente la fecha de hoy</div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="fechaDevolucion" class="form-label">Fecha de Devolución <span class="text-danger">*</span></label>
                                    <input type="date" class="form-control" id="fechaDevolucion" name="fechaDevolucion" required>
                                    <div class="form-text text-muted">No puede ser anterior a la fecha de préstamo</div>
                                </div>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-secondary">Cancelar</a>
                                <button type="submit" class="btn btn-warning">
                                    <i class="bi bi-save"></i> Registrar Préstamo
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Fecha de hoy en formato YYYY-MM-DD
        const hoy = new Date().toISOString().split('T')[0];

        // Fecha de préstamo = solo hoy (bloqueada)
        const fechaPrestamo = document.getElementById('fechaPrestamo');
        fechaPrestamo.value = hoy;
        fechaPrestamo.min = hoy;
        fechaPrestamo.max = hoy;

        // Fecha de devolución: no puede ser anterior a hoy
        const fechaDevolucion = document.getElementById('fechaDevolucion');
        fechaDevolucion.min = hoy;

        // Valor por defecto: hoy + 15 días
        let devolucion = new Date();
        devolucion.setDate(devolucion.getDate() + 15);
        fechaDevolucion.value = devolucion.toISOString().split('T')[0];
    </script>
</body>
</html>