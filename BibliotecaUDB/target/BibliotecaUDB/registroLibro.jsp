<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="udb.biblioteca.CategoriaBean" %>
<%@ page import="java.util.List" %>
<%
    // Cargar categorías para el select dinámico
    CategoriaBean catBean = new CategoriaBean();
    List<CategoriaBean> categorias = catBean.getListaCategorias();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Libro - Biblioteca UDB</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root { --udb-primary: #1a365d; --udb-secondary: #2b6cb0; }
        body { background: #f7fafc; min-height: 100vh; }
        .navbar { background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important; }
        .form-card { border: none; border-radius: 1rem; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }
        .form-card .card-header { background: var(--udb-primary); color: white; border-radius: 1rem 1rem 0 0 !important; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp"><i class="bi bi-book-half"></i> Biblioteca UDB</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="index.jsp"><i class="bi bi-house"></i> Inicio</a>
                <a class="nav-link active" href="registroLibro.jsp">Libros</a>
                <a class="nav-link" href="registroEstudiante.jsp">Estudiantes</a>
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
                        <h4 class="mb-0"><i class="bi bi-journal-plus"></i> Registrar Nuevo Libro</h4>
                    </div>
                    <div class="card-body p-4">
                        <!-- 
                            Los nombres de los campos (name) coinciden exactamente 
                            con las propiedades del JavaBean para property="*"
                        -->
                        <form action="controllerLibro.jsp" method="POST">
                            <div class="mb-3">
                                <label for="titulo" class="form-label fw-semibold">Título *</label>
                                <input type="text" class="form-control" id="titulo" name="titulo"
                                       required maxlength="150" placeholder="Ej: Programación Orientada a Objetos">
                            </div>

                            <div class="mb-3">
                                <label for="autor" class="form-label fw-semibold">Autor *</label>
                                <input type="text" class="form-control" id="autor" name="autor"
                                       required maxlength="100" placeholder="Ej: Herbert Schildt">
                            </div>

                            <div class="mb-3">
                                <label for="isbn" class="form-label fw-semibold">ISBN</label>
                                <input type="text" class="form-control" id="isbn" name="isbn"
                                       maxlength="20" placeholder="Ej: 978-0071808552">
                            </div>

                            <div class="mb-3">
                                <label for="idCategoria" class="form-label fw-semibold">Categoría *</label>
                                <select class="form-select" id="idCategoria" name="idCategoria" required>
                                    <option value="">-- Seleccione una categoría --</option>
                                    <% for (CategoriaBean cat : categorias) { %>
                                        <option value="<%= cat.getIdCategoria() %>">
                                            <%= cat.getNombreCategoria() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>

                            <div class="mb-4">
                                <label for="cantidadDisponible" class="form-label fw-semibold">Cantidad Disponible *</label>
                                <input type="number" class="form-control" id="cantidadDisponible" name="cantidadDisponible"
                                       required min="1" value="1">
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="index.jsp" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-check-circle"></i> Registrar Libro
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
