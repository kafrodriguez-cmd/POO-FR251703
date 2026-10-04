<%--
    Archivo de conexión a la base de datos.
    Se incluye en los controladores mediante: <%@ include file="conexion.jsp" %>
    Nota: La lógica principal de conexión está centralizada en ConexionDB.java
          para uso desde los JavaBeans. Este archivo mantiene la referencia
          requerida por la guía de ejercicios.
--%>
<%@ page import="java.sql.*" %>
<%@ page import="udb.biblioteca.ConexionDB" %>
<%
    // Variables de conexión disponibles si se necesitan en el JSP
    Connection conexion = null;
    try {
        conexion = ConexionDB.getConnection();
    } catch (SQLException e) {
        out.println("<!-- Error de conexión: " + e.getMessage() + " -->");
    }
%>
