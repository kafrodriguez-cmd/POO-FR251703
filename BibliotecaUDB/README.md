# Sistema de Gestión de Biblioteca Universitaria
## JSP + JavaBeans — Universidad Don Bosco

### Requisitos previos
1. **JDK 8 o superior**
2. **Apache Tomcat 9 o superior**
3. **MySQL 5.7 / 8.x** (o MariaDB)
4. **NetBeans IDE** (recomendado) o cualquier IDE con soporte Maven
5. **Maven** (incluido en NetBeans)

---

### 1. Configurar la base de datos

1. Abra MySQL (Workbench, phpMyAdmin o consola).
2. Ejecute el script:
   ```
   sql/bibliotecaudb.sql
   ```
3. Verifique que se crearon las 4 tablas y los datos de prueba.

---

### 2. Configurar la conexión

Edite el archivo:
```
src/main/java/udb/biblioteca/ConexionDB.java
```

Ajuste según su entorno:
```java
private static final String URL = "jdbc:mysql://localhost:3306/bibliotecaudb?...";
private static final String USER = "root";
private static final String PASSWORD = "su_contraseña";  // ← Cambiar
```

---

### 3. Importar en NetBeans

1. **File → Open Project** (o New Project → Java with Maven → Project from existing POM)
2. Seleccione la carpeta `BibliotecaUDB` (donde está el `pom.xml`)
3. Espere a que Maven descargue las dependencias (MySQL Connector, JSTL, etc.)
4. Click derecho en el proyecto → **Properties → Run**
   - Server: Apache Tomcat
   - Context Path: `/BibliotecaUDB` (o el que prefiera)
5. Click derecho → **Run** (o Deploy)

---

### 4. Estructura del proyecto

```
BibliotecaUDB/
├── pom.xml                          ← Dependencias Maven (MySQL Connector)
├── sql/
│   └── bibliotecaudb.sql            ← Script de creación + datos de prueba
├── src/main/java/udb/biblioteca/
│   ├── ConexionDB.java              ← Conexión JDBC centralizada
│   ├── CategoriaBean.java           ← Bean + getListaCategorias()
│   ├── LibroBean.java               ← Bean + getListaLibros() + getNombreCategoria()
│   ├── EstudianteBean.java          ← Bean
│   └── PrestamoBean.java            ← Bean + getEstadoPrestamo() + validación + devolución
└── src/main/webapp/
    ├── index.jsp                    ← Menú principal (Bootstrap)
    ├── registroLibro.jsp            ← Formulario de libros (select dinámico)
    ├── registroEstudiante.jsp       ← Formulario de estudiantes
    ├── registroPrestamo.jsp         ← Formulario de préstamos (selects dinámicos)
    ├── listaPrestamos.jsp           ← Tabla de préstamos + botón Devolver
    ├── controllerLibro.jsp          ← useBean + setProperty* + getProperty
    ├── controllerEstudiante.jsp
    ├── controllerPrestamo.jsp
    ├── conexion.jsp                 ← Include de conexión
    └── WEB-INF/
        └── web.xml
```

---

### 5. Funcionalidades implementadas

| Funcionalidad | Estado |
|---|---|
| Registro de libros | ✅ |
| Registro de estudiantes | ✅ |
| Registro de préstamos | ✅ |
| Listado de préstamos con estado Vigente/Vencido | ✅ |
| Selects dinámicos desde BD | ✅ |
| jsp:useBean / setProperty / getProperty | ✅ |
| PreparedStatement (anti SQL Injection) | ✅ |
| Bootstrap 5 | ✅ |
| **Devolución de préstamos** (puntos extra) | ✅ |
| **Validación de disponibilidad** (puntos extra) | ✅ |

---

### 6. Notas importantes

- Los nombres de los campos HTML (`name`) coinciden exactamente con las propiedades de los JavaBeans para que `property="*"` funcione.
- La conexión se realiza con `PreparedStatement` en todos los Beans.
- No se usan frameworks adicionales (Spring, Hibernate, etc.).
- Compatible con Apache Tomcat 9+.

---

### 7. Documentación breve

Ver archivo: `Documentacion_Proyecto.md`
