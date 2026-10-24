<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="Adopciones"/>
</jsp:include>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
    <h2 class="mb-0"><i class="bi bi-clipboard-heart"></i> Gestión de Adopciones</h2>
    <a href="${pageContext.request.contextPath}/adopciones?accion=nuevo" class="btn btn-primary">
        <i class="bi bi-plus-lg"></i> Nueva Adopción
    </a>
</div>

<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover mb-0 align-middle">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Mascota</th>
                    <th>Especie / Raza</th>
                    <th>Adoptante</th>
                    <th>DUI</th>
                    <th>Teléfono</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th>Registrado por</th>
                    <th class="text-center">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${lista}">
                    <tr>
                        <td>${a.idAdopcion}</td>
                        <td><strong>${a.nombreMascota}</strong></td>
                        <td>${a.especieMascota} / ${a.razaMascota}</td>
                        <td>${a.nombreAdoptante}</td>
                        <td>${a.duiAdoptante}</td>
                        <td>${a.telefonoAdoptante}</td>
                        <td>${a.fechaAdopcion}</td>
                        <td>
                            <span class="badge badge-${a.estado.toLowerCase()}">${a.estado}</span>
                        </td>
                        <td>${a.registradoPor}</td>
                        <td class="text-center text-nowrap">
                            <a href="${pageContext.request.contextPath}/adopciones?accion=editar&id=${a.idAdopcion}"
                               class="btn btn-sm btn-outline-primary btn-action" title="Editar">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <button type="button" class="btn btn-sm btn-outline-danger btn-action" title="Eliminar"
                                    onclick="confirmarEliminar('${pageContext.request.contextPath}/adopciones?accion=eliminar&id=${a.idAdopcion}', 'Adopción #${a.idAdopcion}')">
                                <i class="bi bi-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty lista}">
                    <tr><td colspan="10" class="text-center text-muted py-4">No hay adopciones registradas.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
