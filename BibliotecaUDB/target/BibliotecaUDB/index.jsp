<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca UDB - Sistema de Préstamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .hero {
            background: linear-gradient(135deg, #1e3a5f 0%, #2c5282 100%);
            color: white;
            padding: 60px 0;
            margin-bottom: 40px;
        }
        .card-menu {
            transition: transform 0.2s, box-shadow 0.2s;
            border: none;
            border-radius: 12px;
        }
        .card-menu:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }
        .card-icon {
            font-size: 3rem;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #1e3a5f;">
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
                        <a class="nav-link" href="LibroController?action=listar">Libros</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="EstudianteController?action=listar">Estudiantes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="PrestamoController?action=listar">Préstamos</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Hero -->
    <div class="hero text-center">
        <div class="container">
            <h1 class="display-5 fw-bold">Sistema de Control de Préstamos</h1>
            <p class="lead">Biblioteca Central - Universidad Don Bosco</p>
            <p class="mb-0">POO404 - Desafío Práctico #03</p>
        </div>
    </div>

    <!-- Menú principal -->
    <div class="container mb-5">
        <div class="row g-4">
            <!-- Libros -->
            <div class="col-md-4">
                <div class="card card-menu h-100 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="card-icon text-primary">
                            <i class="bi bi-journal-bookmark-fill"></i>
                        </div>
                        <h4 class="card-title">Libros</h4>
                        <p class="card-text text-muted">Registrar y consultar el catálogo de libros de la biblioteca.</p>
                        <div class="d-grid gap-2">
                            <a href="LibroController?action=nuevo" class="btn btn-primary">
                                <i class="bi bi-plus-circle"></i> Registrar Libro
                            </a>
                            <a href="LibroController?action=listar" class="btn btn-outline-primary">
                                <i class="bi bi-list-ul"></i> Listar Libros
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Estudiantes -->
            <div class="col-md-4">
                <div class="card card-menu h-100 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="card-icon text-success">
                            <i class="bi bi-people-fill"></i>
                        </div>
                        <h4 class="card-title">Estudiantes</h4>
                        <p class="card-text text-muted">Registrar y consultar los estudiantes que pueden solicitar préstamos.</p>
                        <div class="d-grid gap-2">
                            <a href="EstudianteController?action=nuevo" class="btn btn-success">
                                <i class="bi bi-person-plus"></i> Registrar Estudiante
                            </a>
                            <a href="EstudianteController?action=listar" class="btn btn-outline-success">
                                <i class="bi bi-list-ul"></i> Listar Estudiantes
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Préstamos -->
            <div class="col-md-4">
                <div class="card card-menu h-100 shadow-sm">
                    <div class="card-body text-center p-4">
                        <div class="card-icon text-warning">
                            <i class="bi bi-arrow-left-right"></i>
                        </div>
                        <h4 class="card-title">Préstamos</h4>
                        <p class="card-text text-muted">Gestionar préstamos, devoluciones y consultar el estado actual.</p>
                        <div class="d-grid gap-2">
                            <a href="PrestamoController?action=nuevo" class="btn btn-warning">
                                <i class="bi bi-plus-circle"></i> Nuevo Préstamo
                            </a>
                            <a href="PrestamoController?action=listar" class="btn btn-outline-warning">
                                <i class="bi bi-list-ul"></i> Listar Préstamos
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white text-center py-3 mt-auto">
        <div class="container">
            <small>Universidad Don Bosco &copy; 2026 | POO404 - Programación Orientada a Objetos</small>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
