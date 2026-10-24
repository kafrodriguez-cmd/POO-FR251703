<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="Reporte de Adopciones"/>
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0"><i class="bi bi-file-earmark-bar-graph"></i> Reporte de Adopciones (JOIN)</h2>
    <button class="btn btn-outline-secondary" onclick="window.print()">
        <i class="bi bi-printer"></i> Imprimir
    </button>
</div>

<div class="alert alert-info">
    <i class="bi bi-info-circle"></i>
    Este reporte utiliza una consulta SQL con <strong>JOIN</strong> entre las tablas
    <code>adopcion</code>, <code>mascota</code>, <code>adoptante</code> y <code>usuario</code>.
</div>

<div class="card shadow-sm border-0">
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-bordered table-hover mb-0">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Fecha</th>
                    <th>Mascota</th>
                    <th>Especie</th>
                    <th>Raza</th>
                    <th>Adoptante</th>
                    <th>DUI</th>
                    <th>Teléfono</th>
                    <th>Estado</th>
                    <th>Observaciones</th>
                    <th>Registrado por</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${lista}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td>${a.fechaAdopcion}</td>
                        <td><strong>${a.nombreMascota}</strong></td>
                        <td>${a.especieMascota}</td>
                        <td>${a.razaMascota}</td>
                        <td>${a.nombreAdoptante}</td>
                        <td>${a.duiAdoptante}</td>
                        <td>${a.telefonoAdoptante}</td>
                        <td><span class="badge badge-${a.estado.toLowerCase()}">${a.estado}</span></td>
                        <td>${a.observaciones}</td>
                        <td>${a.registradoPor}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty lista}">
                    <tr><td colspan="11" class="text-center text-muted py-4">Sin registros.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<p class="text-muted mt-2 small">Total de registros: <strong>${lista.size()}</strong></p>

<jsp:include page="../common/footer.jsp"/>
