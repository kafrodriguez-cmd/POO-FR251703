<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="Mascotas"/>
</jsp:include>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
    <h2 class="mb-0"><i class="bi bi-paw"></i> Gestión de Mascotas</h2>
    <a href="${pageContext.request.contextPath}/mascotas?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-lg"></i> Nueva Mascota
    </a>
</div>

<!-- Filtros de búsqueda -->
<div class="card shadow-sm border-0 mb-3">
    <div class="card-body">
        <form method="get" action="${pageContext.request.contextPath}/mascotas" class="row g-2 align-items-end">
            <input type="hidden" name="accion" value="buscar">
            <div class="col-md-4">
                <label class="form-label">Buscar</label>
                <input type="text" name="criterio" class="form-control" placeholder="Nombre, raza o color..."
                       value="${criterio}">
            </div>
            <div class="col-md-3">
                <label class="form-label">Especie</label>
                <select name="especie" class="form-select">
                    <option value="Todas">Todas</option>
                    <c:forEach var="e" items="${['Perro','Gato','Conejo','Ave','Otro']}">
                        <option value="${e}" ${especieFiltro == e ? 'selected' : ''}>${e}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Estado</label>
                <select name="estado" class="form-select">
                    <option value="Todos">Todos</option>
                    <c:forEach var="e" items="${['Disponible','En proceso','Adoptada','No disponible']}">
                        <option value="${e}" ${estadoFiltro == e ? 'selected' : ''}>${e}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-outline-primary w-100">
                    <i class="bi bi-search"></i> Filtrar
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
                    <th>Nombre</th>
                    <th>Especie</th>
                    <th>Raza</th>
                    <th>Edad</th>
                    <th>Sexo</th>
                    <th>Tamaño</th>
                    <th>Estado</th>
                    <th>Ingreso</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="m" items="${lista}">
                    <tr>
                        <td>${m.idMascota}</td>
                        <td><strong>${m.nombre}</strong></td>
                        <td>${m.especie}</td>
                        <td>${m.raza}</td>
                        <td>${m.edadLegible}</td>
                        <td>${m.sexo}</td>
                        <td>${m.tamano}</td>
                        <td>
                            <span class="badge badge-${m.estado.toLowerCase().replace(' ', '')}">${m.estado}</span>
                        </td>
                        <td>${m.fechaIngreso}</td>
                        <td class="text-center text-nowrap">
                            <a href="${pageContext.request.contextPath}/mascotas?accion=editar&id=${m.idMascota}"
                               class="btn btn-sm btn-outline-primary btn-action" title="Editar">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger btn-action" title="Eliminar"
                                    onclick="confirmarEliminar('${pageContext.request.contextPath}/mascotas?accion=eliminar&id=${m.idMascota}', '${m.nombre}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty lista}">
                    <tr><td colspan="10" class="text-center text-muted py-4">No se encontraron mascotas.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
