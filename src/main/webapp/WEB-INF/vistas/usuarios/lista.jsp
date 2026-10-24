<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="Trabajadores"/>
</jsp:include>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
    <h2 class="mb-0"><i class="bi bi-person-badge"></i> Listado de Trabajadores</h2>
    <a href="${pageContext.request.contextPath}/trabajadores?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-lg"></i> Nuevo Trabajador
    </a>
</div>

<div class="alert alert-secondary">
    <i class="bi bi-info-circle"></i>
    Aquí se gestionan los <strong>trabajadores / empleados</strong> que pueden iniciar sesión en el sistema.
    Solo los usuarios con rol <strong>ADMIN</strong> tienen acceso a este módulo.
</div>

<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover mb-0 align-middle">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Usuario</th>
                    <th>Nombre completo</th>
                    <th>Rol</th>
                    <th>Estado</th>
                    <th>Fecha registro</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${lista}">
                    <tr>
                        <td>${u.idUsuario}</td>
                        <td><code>${u.username}</code></td>
                        <td>${u.nombreCompleto}</td>
                        <td>
                            <span class="badge ${u.rol == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">${u.rol}</span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.activo}">
                                    <span class="badge bg-success">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${u.fechaRegistro}</td>
                        <td class="text-center text-nowrap">
                            <a href="${pageContext.request.contextPath}/trabajadores?accion=editar&id=${u.idUsuario}"
                               class="btn btn-sm btn-outline-primary btn-action" title="Editar">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger btn-action" title="Eliminar"
                                    onclick="confirmarEliminar('${pageContext.request.contextPath}/trabajadores?accion=eliminar&id=${u.idUsuario}', '${u.nombreCompleto}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty lista}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay trabajadores registrados.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
