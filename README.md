# 🐾 Huellitas Felices – Centro de Adopción de Mascotas

Sistema web completo desarrollado como **Proyecto de Cátedra** de la materia  
**Programación Orientada a Objetos (POO404)** – Universidad Don Bosco.

## Tecnologías

| Capa          | Tecnología                          |
|---------------|-------------------------------------|
| Modelo        | JavaBeans + DAO (JDBC)              |
| Controlador   | Servlets (`@WebServlet`)            |
| Vista         | JSP + JSTL + Bootstrap 5            |
| Persistencia  | MySQL 8                             |
| Servidor      | Apache Tomcat 10+                   |
| Build         | Maven                               |

## Estructura del proyecto

```
HuellitasFelices/
├── pom.xml
├── sql/
│   └── huellitas_felices.sql          ← Script de BD + datos de prueba
├── docs/
│   └── Documento_Proyecto.md          ← Documento formal del proyecto
├── src/main/java/com/huellitas/
│   ├── modelo/                        ← JavaBeans
│   ├── dao/                           ← Data Access Objects
│   ├── controlador/                   ← Servlets
│   └── util/                          ← Conexion.java
└── src/main/webapp/
    ├── index.jsp
    ├── css/estilos.css
    └── WEB-INF/vistas/
        ├── auth/login.jsp
        ├── common/ (header + footer)
        ├── mascotas/
        ├── adoptantes/
        ├── adopciones/
        ├── reportes/
        └── dashboard.jsp
```

## Cómo ejecutar

1. **Base de datos**  
   Ejecutar `sql/huellitas_felices.sql` en MySQL.

2. **Configurar conexión**  
   Editar `src/main/java/com/huellitas/util/Conexion.java` si es necesario  
   (usuario/password de MySQL).

3. **NetBeans**  
   - File → Open Project → seleccionar carpeta `HuellitasFelices`  
   - Clic derecho → Run  

4. **Credenciales**  
   - Admin: `admin` / `admin123`  
   - Empleado: `empleado` / `emp123`

## Funcionalidades

- ✅ Login con manejo de sesión (`HttpSession`)
- ✅ CRUD completo de Mascotas (con búsqueda y filtros)
- ✅ CRUD completo de Adoptantes (con búsqueda)
- ✅ CRUD completo de Adopciones
- ✅ Reporte con JOIN (mascota + adoptante + usuario)
- ✅ Dashboard con contadores
- ✅ Interfaz responsive (Bootstrap 5)
- ✅ Mensajes de éxito/error
- ✅ Confirmación antes de eliminar

## Entidades y relaciones

- **Usuario** 1──N **Adopcion**
- **Mascota** 1──N **Adopcion** N──1 **Adoptante**
- **Mascota** N──M **Vacuna** (tabla intermedia `mascota_vacuna`)
