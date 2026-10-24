<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="${usuarioEdit != null && usuarioEdit.idUsuario > 0 ? 'Editar Trabajador' : 'Nuevo Trabajador'}"/>
</jsp:include>

<div class="row justify-content-center">
    <div class="col-lg-6">
        <div class="card form-card">
            <div class="card-header bg-primary text-white fw-semibold">
                <i class="bi bi-person-badge"></i>
                ${usuarioEdit != null && usuarioEdit.idUsuario > 0 ? 'Editar Trabajador' : 'Registrar Nuevo Trabajador'}
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/trabajadores">
                    <c:if test="${usuarioEdit != null && usuarioEdit.idUsuario > 0}">
                        <input type="hidden" name="idUsuario" value="${usuarioEdit.idUsuario}">
                    </c:if>

                    <div class="mb-3">
                        <label class="form-label required">Nombre de usuario</label>
                        <input type="text" name="username" class="form-control" required
                               value="${usuarioEdit.username}" maxlength="50">
                    </div>
                    <div class="mb-3">
                        <label class="form-label ${usuarioEdit == null || usuarioEdit.idUsuario == 0 ? 'required' : ''}">
                            Contraseña
                        </label>
                        <input type="password" name="password" class="form-control"
                               ${usuarioEdit == null || usuarioEdit.idUsuario == 0 ? 'required' : ''}
                               placeholder="${usuarioEdit != null && usuarioEdit.idUsuario > 0 ? 'Dejar en blanco para no cambiar' : ''}">
                    </div>
                    <div class="mb-3">
                        <label class="form-label required">Nombre completo</label>
                        <input type="text" name="nombreCompleto" class="form-control" required
                               value="${usuarioEdit.nombreCompleto}" maxlength="120">
                    </div>
                    <div class="mb-3">
                        <label class="form-label required">Rol</label>
                        <select name="rol" class="form-select" required>
                            <option value="EMPLEADO" ${usuarioEdit.rol == 'EMPLEADO' ? 'selected' : ''}>EMPLEADO</option>
                            <option value="ADMIN"    ${usuarioEdit.rol == 'ADMIN'    ? 'selected' : ''}>ADMIN</option>
                        </select>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" name="activo" class="form-check-input" id="activo"
                               ${usuarioEdit == null || usuarioEdit.activo ? 'checked' : ''}>
                        <label class="form-check-label" for="activo">Usuario activo</label>
                    </div>

                    <div class="mt-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-save"></i> Guardar
                        </button>
                        <a href="${pageContext.request.contextPath}/trabajadores" class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
