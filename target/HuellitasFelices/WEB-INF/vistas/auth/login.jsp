<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión | Huellitas Felices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body>
<div class="login-wrapper">
    <div class="card login-card">
        <div class="card-header">
            <div class="login-logo"><i class="bi bi-heart-fill"></i></div>
            <h3 class="fw-bold text-primary mb-0">Huellitas Felices</h3>
            <p class="text-muted small">Centro de Adopción de Mascotas</p>
        </div>
        <div class="card-body px-4 pb-4">
            <c:if test="${not empty error}">
                <div class="alert alert-danger py-2">
                    <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/login" autocomplete="off">
                <div class="mb-3">
                    <label class="form-label" for="username">Usuario</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                        <input type="text" class="form-control" id="username" name="username"
                               value="${username}" required autofocus placeholder="Ingrese su usuario">
                    </div>
                </div>
                <div class="mb-4">
                    <label class="form-label" for="password">Contraseña</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-lock"></i></span>
                        <input type="password" class="form-control" id="password" name="password"
                               required placeholder="Ingrese su contraseña">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">
                    <i class="bi bi-box-arrow-in-right"></i> Ingresar
                </button>
            </form>

            <hr class="my-4">
            <div class="text-center text-muted small">
                <strong>Credenciales de prueba:</strong><br>
                Admin: <code>admin</code> / <code>admin123</code><br>
                Empleado: <code>empleado</code> / <code>emp123</code>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
