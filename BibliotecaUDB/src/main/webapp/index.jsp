<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca UDB - Sistema de Gestión</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        :root {
            --udb-primary: #1a365d;
            --udb-secondary: #2b6cb0;
            --udb-accent: #ed8936;
        }
        body {
            background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
            min-height: 100vh;
        }
        .navbar {
            background: linear-gradient(90deg, var(--udb-primary), var(--udb-secondary)) !important;
            box-shadow: 0 2px 10px rgba(0,0,0,0.15);
        }
        .hero {
            background: linear-gradient(135deg, var(--udb-primary) 0%, var(--udb-secondary) 100%);
            color: white;
            padding: 3rem 0;
            border-radius: 0 0 2rem 2rem;
            margin-bottom: 2rem;
        }
        .card-menu {
            border: none;
            border-radius: 1rem;
            transition: transform 0.3s, box-shadow 0.3s;
            overflow: hidden;
        }
        .card-menu:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 30px rgba(0,0,0,0.15);
        }
        .card-menu .card-body {
            padding: 2rem;
        }
        .icon-circle {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.8rem;
            margin: 0 auto 1rem;
        }
        .bg-libros { background: #ebf8ff; color: #2b6cb0; }
        .bg-estudiantes { background: #f0fff4; color: #38a169; }
        .bg-prestamos { background: #fffaf0; color: #dd6b20; }
        .footer {
            background: var(--udb-primary);
            color: white;
            padding: 1.5rem 0;
            margin-top: 3rem;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.jsp">
                <i class="bi bi-book-half"></i> Biblioteca UDB
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.jsp"><i class="bi bi-house"></i> Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registroLibro.jsp"><i class="bi bi-journal-plus"></i> Libros</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registroEstudiante.jsp"><i class="bi bi-person-plus"></i> Estudiantes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="registroPrestamo.jsp"><i class="bi bi-arrow-left-right"></i> Préstamos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="listaPrestamos.jsp"><i class="bi bi-list-ul"></i> Listado</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero -->
    <div class="hero text-center">
        <div class="container">
            <h1 class="display-5 fw-bold">
                <i class="bi bi-building"></i> Sistema de Gestión de Biblioteca
            </h1>
            <p class="lead mb-0">Universidad Don Bosco — Facultad de Ingeniería</p>
            <p class="mb-0">Programación Orientada a Objetos — Ciclo II</p>
        </div>
    </div>

    <!-- Menú de secciones -->
    <div class="container">
        <div class="row g-4 justify-content-center">
            <!-- Libros -->
            <div class="col-md-4">
                <div class="card card-menu shadow-sm h-100">
                    <div class="card-body text-center">
                        <div class="icon-circle bg-libros">
                            <i class="bi bi-journal-bookmark-fill"></i>
                        </div>
                        <h4 class="card-title">Libros</h4>
                        <p class="card-text text-muted">
                            Registrar nuevos libros en el catálogo de la biblioteca.
                            Incluye título, autor, ISBN, categoría y cantidad disponible.
                        </p>
                        <a href="registroLibro.jsp" class="btn btn-primary">
                            <i class="bi bi-plus-circle"></i> Registrar Libro
                        </a>
                    </div>
                </div>
            </div>

            <!-- Estudiantes -->
            <div class="col-md-4">
                <div class="card card-menu shadow-sm h-100">
                    <div class="card-body text-center">
                        <div class="icon-circle bg-estudiantes">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <h4 class="card-title">Estudiantes</h4>
                        <p class="card-text text-muted">
                            Registrar estudiantes de la universidad.
                            Carnet, nombre, carrera y teléfono de contacto.
                        </p>
                        <a href="registroEstudiante.jsp" class="btn btn-success">
                            <i class="bi bi-person-plus"></i> Registrar Estudiante
                        </a>
                    </div>
                </div>
            </div>

            <!-- Préstamos -->
            <div class="col-md-4">
                <div class="card card-menu shadow-sm h-100">
                    <div class="card-body text-center">
                        <div class="icon-circle bg-prestamos">
                            <i class="bi bi-arrow-left-right"></i>
                        </div>
                        <h4 class="card-title">Préstamos</h4>
                        <p class="card-text text-muted">
                            Registrar préstamos de libros a estudiantes
                            y consultar el listado de préstamos vigentes y vencidos.
                        </p>
                        <a href="registroPrestamo.jsp" class="btn btn-warning text-dark">
                            <i class="bi bi-bookmark-plus"></i> Nuevo Préstamo
                        </a>
                        <a href="listaPrestamos.jsp" class="btn btn-outline-warning mt-2">
                            <i class="bi bi-list-check"></i> Ver Listado
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer text-center">
        <div class="container">
            <p class="mb-0">
                &copy; 2026 Universidad Don Bosco — Escuela de Computación<br>
                <small>Proyecto JSP + JavaBeans | Arquitectura MVC simplificada</small>
            </p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
