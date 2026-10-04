<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="udb.biblioteca.EstudianteBean" %>
<%@ page import="udb.biblioteca.LibroBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%
    // Cargar listas dinámicas para los selects
    EstudianteBean estBean = new EstudianteBean();
    List<EstudianteBean> estudiantes = estBean.getListaEstudiantes();

    LibroBean libBean = new LibroBean();
    List<LibroBean> libros = libBean.getListaLibros();

    // Fechas por defecto
    String hoy = LocalDate.now().toString();
    String devolucionDefault = LocalDate.now().plusDays(15).toString();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Préstamo - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root { --udb-primary: #1a365d; --udb-secondary: #2b6cb0; }
        body { background: #f7fafc; min-height: 100vh; }
        .navbar { background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important; }
        .form-card { border: none; border-radius: 1rem; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        .form-card .card-header { background: #dd6b20; color: white; border-radius: 1rem 1rem 0 0 !important; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-book-half"></i> Biblioteca UDB</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp"><i class="bi bi-house"></i> Inicio</a>
                <a class="nav-link" href="registroLibro.jsp">Libros</a>
                <a class="nav-link" href="registroEstudiante.jsp">Estudiantes</a>
                <a class="nav-link active" href="registroPrestamo.jsp">Préstamos</a>
                <a class="nav-link" href="listaPrestamos.jsp">Listado</a>
            </div>
        </div>
    </nav>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card form-card">
                    <div class="card-header py-3">
                        <h4 class="mb-0"><i class="bi bi-bookmark-plus"></i> Registrar Nuevo Préstamo</h4>
                    </div>
                    <div class="card-body p-4">
                        <form action="controllerPrestamo.jsp" method="POST">
                            <div class="mb-3">
                                <label for="idEstudiante" class="form-label fw-semibold">Estudiante *</label>
                                <select class="form-select" id="idEstudiante" name="idEstudiante" required>
                                    <option value="">-- Seleccione un estudiante --</option>
                                    <% for (EstudianteBean est : estudiantes) { %>
                                        <option value="<%= est.getIdEstudiante() %>">
                                            <%= est.getCarnet() %> - <%= est.getNombreEstudiante() %>
                                            (<%= est.getCarrera() %>)
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="idLibro" class="form-label fw-semibold">Libro *</label>
                                <select class="form-select" id="idLibro" name="idLibro" required>
                                    <option value="">-- Seleccione un libro --</option>
                                    <% for (LibroBean lib : libros) { %>
                                        <option value="<%= lib.getIdLibro() %>"
                                            <%= lib.getCantidadDisponible() <= 0 ? "disabled" : "" %>>
                                            <%= lib.getTitulo() %> — <%= lib.getAutor() %>
                                            (Disp: <%= lib.getCantidadDisponible() %>)
                                            <%= lib.getCantidadDisponible() <= 0 ? " [NO DISPONIBLE]" : "" %>
                                        </option>
                                    <% } %>
                                </select>
                                <div class="form-text">Los libros sin ejemplares disponibles aparecen deshabilitados.</div>
                            </div>

                            <div class="mb-3">
                                <label for="fechaPrestamo" class="form-label fw-semibold">Fecha de Préstamo *</label>
                                <input type="date" class="form-control" id="fechaPrestamo" name="fechaPrestamo"
                                       required value="<%= hoy %>">
                            </div>

                            <div class="mb-4">
                                <label for="fechaDevolucion" class="form-label fw-semibold">Fecha de Devolución *</label>
                                <input type="date" class="form-control" id="fechaDevolucion" name="fechaDevolucion"
                                       required value="<%= devolucionDefault %>">
                            </div>

                            <!-- Estado por defecto: Activo (oculto, se asigna en el bean) -->
                            <input type="hidden" name="estado" value="Activo">

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-warning text-dark">
                                    <i class="bi bi-check-circle"></i> Registrar Préstamo
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
