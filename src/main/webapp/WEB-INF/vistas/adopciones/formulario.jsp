<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="${adopcion != null && adopcion.idAdopcion > 0 ? 'Editar Adopción' : 'Nueva Adopción'}"/>
</jsp:include>

<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card form-card">
            <div class="card-header bg-primary text-white fw-semibold">
                <i class="bi bi-clipboard-heart"></i>
                ${adopcion != null && adopcion.idAdopcion > 0 ? 'Editar Adopción' : 'Registrar Nueva Adopción'}
            </div>
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/adopciones">
                    <c:if test="${adopcion != null && adopcion.idAdopcion > 0}">
                        <input type="hidden" name="idAdopcion" value="${adopcion.idAdopcion}">
                    </c:if>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label required">Mascota</label>
                            <select name="idMascota" class="form-select" required>
                                <option value="">-- Seleccione mascota --</option>
                                <c:forEach var="m" items="${mascotas}">
                                    <option value="${m.idMascota}"
                                        ${(adopcion.idMascota == m.idMascota) ? 'selected' : ''}>
                                        ${m.nombre} (${m.especie} - ${m.estado})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Adoptante</label>
                            <select name="idAdoptante" class="form-select" required>
                                <option value="">-- Seleccione adoptante --</option>
                                <c:forEach var="ad" items="${adoptantes}">
                                    <option value="${ad.idAdoptante}"
                                        ${(adopcion.idAdoptante == ad.idAdoptante) ? 'selected' : ''}>
                                        ${ad.nombreCompleto} - ${ad.dui}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Fecha de adopción / cita</label>
                            <input type="date" name="fechaAdopcion" id="fechaAdopcion" class="form-control" required
                                   value="${adopcion.fechaAdopcion}">
                            <div class="form-text">Solo hoy o fechas futuras (dentro del año actual).</div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Estado</label>
                            <select name="estado" class="form-select" required>
                                <c:forEach var="e" items="${['Pendiente','Aprobada','Rechazada','Finalizada']}">
                                    <option value="${e}" ${adopcion.estado == e ? 'selected' : ''}>${e}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Observaciones</label>
                            <textarea name="observaciones" class="form-control" rows="3">${adopcion.observaciones}</textarea>
                        </div>
                    </div>

                    <div class="mt-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-save"></i> Guardar
                        </button>
                        <a href="${pageContext.request.contextPath}/adopciones" class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Fecha de adopción/cita: mínimo = hoy, máximo = 31-dic del año actual
    (function() {
        const input = document.getElementById('fechaAdopcion');
        if (!input) return;
        const hoy = new Date();
        const yyyy = hoy.getFullYear();
        const mm = String(hoy.getMonth() + 1).padStart(2, '0');
        const dd = String(hoy.getDate()).padStart(2, '0');
        input.min = yyyy + '-' + mm + '-' + dd;   // solo hoy o futuro
        input.max = yyyy + '-12-31';               // no años futuros
        input.addEventListener('change', function() {
            if (this.value) {
                const anio = parseInt(this.value.substring(0, 4), 10);
                if (anio > yyyy) {
                    alert('No se permiten fechas de años futuros (' + yyyy + ').');
                    this.value = '';
                }
            }
        });
    })();
</script>
<jsp:include page="../common/footer.jsp"/>
