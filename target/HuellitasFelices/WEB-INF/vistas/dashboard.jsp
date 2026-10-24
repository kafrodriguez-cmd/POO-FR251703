<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="common/header.jsp">
    <jsp:param name="titulo" value="Dashboard"/>
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="mb-0"><i class="bi bi-speedometer2"></i> Panel Principal</h2>
    <span class="text-muted">Bienvenido/a, <strong>${sessionScope.nombreUsuario}</strong></span>
</div>

<!-- Tarjetas de resumen -->
<div class="row g-3 mb-4">
    <div class="col-sm-6 col-xl-3">
        <div class="card stat-card bg-primary text-white">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <div class="stat-number">${totalMascotas}</div>
                    <div>Mascotas registradas</div>
                </div>
                <i class="bi bi-paw stat-icon"></i>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-xl-3">
        <div class="card stat-card bg-success text-white">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <div class="stat-number">${mascotasDisponibles}</div>
                    <div>Disponibles</div>
                </div>
                <i class="bi bi-heart-fill stat-icon"></i>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-xl-3">
        <div class="card stat-card bg-info text-white">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <div class="stat-number">${totalAdoptantes}</div>
                    <div>Adoptantes</div>
                </div>
                <i class="bi bi-people-fill stat-icon"></i>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-xl-3">
        <div class="card stat-card bg-warning text-dark">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div>
                    <div class="stat-number">${totalAdopciones}</div>
                    <div>Adopciones</div>
                </div>
                <i class="bi bi-clipboard-heart-fill stat-icon"></i>
            </div>
        </div>
    </div>
</div>

<!-- Últimas adopciones -->
<div class="card shadow-sm border-0">
    <div class="card-header bg-white fw-semibold">
        <i class="bi bi-clock-history"></i> Últimas adopciones registradas
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Mascota</th>
                    <th>Adoptante</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th>Registrado por</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${ultimasAdopciones}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td><strong>${a.nombreMascota}</strong> <small class="text-muted">(${a.especieMascota})</small></td>
                        <td>${a.nombreAdoptante}</td>
                        <td>${a.fechaAdopcion}</td>
                        <td>
                            <span class="badge badge-${a.estado.toLowerCase()}">${a.estado}</span>
                        </td>
                        <td>${a.registradoPor}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty ultimasAdopciones}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay adopciones registradas aún.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
