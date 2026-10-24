<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="${mascota != null && mascota.idMascota > 0 ? 'Editar Mascota' : 'Nueva Mascota'}"/>
</jsp:include>

<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card form-card">
            <div class="card-header bg-primary text-white fw-semibold">
                <i class="bi bi-paw"></i>
                ${mascota != null && mascota.idMascota > 0 ? 'Editar Mascota' : 'Registrar Nueva Mascota'}
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/mascotas">
                    <c:if test="${mascota != null && mascota.idMascota > 0}">
                        <input type="hidden" name="idMascota" value="${mascota.idMascota}">
                    </c:if>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label required">Nombre</label>
                            <input type="text" name="nombre" class="form-control" required
                                   value="${mascota.nombre}" maxlength="60">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Especie</label>
                            <select name="especie" class="form-select" required>
                                <option value="">-- Seleccione --</option>
                                <c:forEach var="e" items="${['Perro','Gato','Conejo','Ave','Otro']}">
                                    <option value="${e}" ${mascota.especie == e ? 'selected' : ''}>${e}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Raza</label>
                            <input type="text" name="raza" class="form-control" value="${mascota.raza}" maxlength="60">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Edad (meses)</label>
                            <input type="number" name="edadAprox" class="form-control" min="0" max="300"
                                   value="${mascota.edadAprox}">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label required">Sexo</label>
                            <select name="sexo" class="form-select" required>
                                <option value="Macho"  ${mascota.sexo == 'Macho'  ? 'selected' : ''}>Macho</option>
                                <option value="Hembra" ${mascota.sexo == 'Hembra' ? 'selected' : ''}>Hembra</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Color</label>
                            <input type="text" name="color" class="form-control" value="${mascota.color}" maxlength="40">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Tamaño</label>
                            <select name="tamano" class="form-select">
                                <c:forEach var="t" items="${['Pequeño','Mediano','Grande']}">
                                    <option value="${t}" ${mascota.tamano == t ? 'selected' : ''}>${t}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label required">Estado</label>
                            <select name="estado" class="form-select" required>
                                <c:forEach var="e" items="${['Disponible','En proceso','Adoptada','No disponible']}">
                                    <option value="${e}" ${mascota.estado == e ? 'selected' : ''}>${e}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Fecha de ingreso / rescate</label>
                            <input type="date" name="fechaIngreso" id="fechaIngreso" class="form-control" required
                                   value="${mascota.fechaIngreso}">
                            <div class="form-text">No puede ser una fecha futura. Máximo: hoy.</div>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">URL de foto</label>
                            <input type="text" name="fotoUrl" class="form-control"
                                   value="${mascota.fotoUrl}" placeholder="default_mascota.png">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Descripción</label>
                            <textarea name="descripcion" class="form-control" rows="3">${mascota.descripcion}</textarea>
                        </div>
                    </div>

                    <div class="mt-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-save"></i> Guardar
                        </button>
                        <a href="${pageContext.request.contextPath}/mascotas" class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    // Fecha de ingreso/rescate: máximo = hoy, y no años futuros
    (function() {
        const input = document.getElementById('fechaIngreso');
        if (!input) return;
        const hoy = new Date();
        const yyyy = hoy.getFullYear();
        const mm = String(hoy.getMonth() + 1).padStart(2, '0');
        const dd = String(hoy.getDate()).padStart(2, '0');
        input.max = yyyy + '-' + mm + '-' + dd;   // no fechas futuras
        // Opcional: limitar también el año máximo al actual
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
