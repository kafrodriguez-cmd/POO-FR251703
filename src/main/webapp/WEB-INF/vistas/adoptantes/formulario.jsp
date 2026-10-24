<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="titulo" value="${adoptante != null && adoptante.idAdoptante > 0 ? 'Editar Adoptante' : 'Nuevo Adoptante'}"/>
</jsp:include>

<div class="row justify-content-center">
    <div class="col-lg-7">
        <div class="card form-card">
            <div class="card-header bg-primary text-white fw-semibold">
                <i class="bi bi-person-plus"></i>
                ${adoptante != null && adoptante.idAdoptante > 0 ? 'Editar Adoptante' : 'Registrar Nuevo Adoptante'}
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <form method="post" action="${pageContext.request.contextPath}/adoptantes">
                    <c:if test="${adoptante != null && adoptante.idAdoptante > 0}">
                        <input type="hidden" name="idAdoptante" value="${adoptante.idAdoptante}">
                    </c:if>

                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label required">Nombres</label>
                            <input type="text" name="nombres" class="form-control" required
                                   value="${adoptante.nombres}" maxlength="80">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">Apellidos</label>
                            <input type="text" name="apellidos" class="form-control" required
                                   value="${adoptante.apellidos}" maxlength="80">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label required">DUI</label>
                            <input type="text" name="dui" class="form-control" required
                                   value="${adoptante.dui}" maxlength="15" placeholder="00000000-0">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Teléfono</label>
                            <input type="text" name="telefono" class="form-control"
                                   value="${adoptante.telefono}" maxlength="20">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control"
                                   value="${adoptante.email}" maxlength="100">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Dirección</label>
                            <input type="text" name="direccion" class="form-control"
                                   value="${adoptante.direccion}" maxlength="200">
                        </div>
                    </div>

                    <div class="mt-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-save"></i> Guardar
                        </button>
                        <a href="${pageContext.request.contextPath}/adoptantes" class="btn btn-outline-secondary">
                            Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
