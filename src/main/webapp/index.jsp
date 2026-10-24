<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redirige al login o al dashboard según si hay sesión
    response.sendRedirect(request.getContextPath() + "/login");
%>
