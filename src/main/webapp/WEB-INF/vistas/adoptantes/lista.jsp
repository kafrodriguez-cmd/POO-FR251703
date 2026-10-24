<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="Adoptantes"/>
</jsp:include>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
    <h2 class="mb-0"><i class="bi bi-people"></i> Gestión de Adoptantes</h2>
    <a href="${pageContext.request.contextPath}/adoptantes?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-lg"></i> Nuevo Adoptante
    </a>
</div>

<div class="card shadow-sm border-0 mb-3">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/adoptantes" class="row g-2">
            <input type="hidden" name="accion" value="buscar">
            <div class="col-md-8">
                <input type="text" name="criterio" class="form-control"
                       placeholder="Buscar por nombre, apellido o DUI..." value="${criterio}">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-outline-primary w-100">
                    <i class="bi bi-search"></i> Buscar
                </button>
            </div>
        </form>
    </div>
</div>

<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover mb-0 align-middle">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                    <th>DUI</th>
                    <th>Teléfono</th>
                    <th>Email</th>
                    <th>Dirección</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${lista}">
                    <tr>
                        <td>${a.idAdoptante}</td>
                        <td>${a.nombres}</td>
                        <td>${a.apellidos}</td>
                        <td>${a.dui}</td>
                        <td>${a.telefono}</td>
                        <td>${a.email}</td>
                        <td>${a.direccion}</td>
                        <td class="text-center text-nowrap">
                            <a href="${pageContext.request.contextPath}/adoptantes?accion=editar&id=${a.idAdoptante}"
                               class="btn btn-sm btn-outline-primary btn-action" title="Editar">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger btn-action" title="Eliminar"
                                    onclick="confirmarEliminar('${pageContext.request.contextPath}/adoptantes?accion=eliminar&id=${a.idAdoptante}', '${a.nombreCompleto}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty lista}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No se encontraron adoptantes.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
