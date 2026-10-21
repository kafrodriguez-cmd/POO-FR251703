# BibliotecaUDB - Sistema de Control de Préstamos

**Universidad Don Bosco**  
**Materia:** Programación Orientada a Objetos (POO404)  
**Desafío Práctico #03**

Aplicación web desarrollada con arquitectura **MVC** usando:
- **JSTL** + Expression Language
- **JavaBeans**
- **JDBC** (MySQL)
- **Bootstrap 5**

---

## Requisitos previos

1. **NetBeans** 17 o superior (con soporte Maven)
2. **JDK 17** o superior
3. **Apache Tomcat 10.1** o superior (Jakarta EE)
4. **MySQL / MariaDB** (WAMP, XAMPP o MySQL Server)
5. **phpMyAdmin** (recomendado)

---

## 1. Crear la Base de Datos

1. Abra **phpMyAdmin** o el cliente MySQL.
2. Ejecute el script completo ubicado en:
   ```
   sql/bibliotecaudb.sql
   ```
3. Verifique que se crearon las 4 tablas y los datos de prueba.

---

## 2. Importar el proyecto en NetBeans

### Opción A – Abrir proyecto Maven existente
1. En NetBeans: **File → Open Project**
2. Seleccione la carpeta `BibliotecaUDB`
3. NetBeans detectará automáticamente que es un proyecto Maven.

### Opción B – Crear proyecto nuevo y copiar archivos
1. **File → New Project → Java with Maven → Web Application**
2. Group Id: `edu.udb`
3. Artifact Id: `BibliotecaUDB`
4. Server: Apache Tomcat 10.x
5. Java EE Version: Jakarta EE 10
6. Una vez creado, reemplace el contenido de `pom.xml` y copie las carpetas `src/main/java` y `src/main/webapp`.

---

## 3. Configurar la conexión a MySQL

Edite el archivo:
```
src/main/java/util/Conexion.java
```

Ajuste si es necesario:
```java
private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bibliotecaudb?...";
private static final String JDBC_USER = "root";
private static final String JDBC_PASS = "";   // ← ponga su contraseña si tiene
```

---

## 4. Ejecutar el proyecto

1. Clic derecho sobre el proyecto → **Run**
2. NetBeans desplegará la aplicación en Tomcat.
3. Abra el navegador en:  
   `http://localhost:8080/BibliotecaUDB/`

---

## Estructura del proyecto (MVC)

```
BibliotecaUDB/
├── pom.xml
├── sql/
│   └── bibliotecaudb.sql
└── src/main/
    ├── java/
    │   ├── models/          ← JavaBeans
    │   │   ├── CategoriaBean.java
    │   │   ├── LibroBean.java
    │   │   ├── EstudianteBean.java
    │   │   └── PrestamoBean.java
    │   ├── dao/             ← Acceso a datos (JDBC)
    │   │   ├── CategoriaDAO.java
    │   │   ├── LibroDAO.java
    │   │   ├── EstudianteDAO.java
    │   │   └── PrestamoDAO.java
    │   ├── controller/      ← Servlets
    │   │   ├── LibroController.java
    │   │   ├── EstudianteController.java
    │   │   └── PrestamoController.java
    │   └── util/
    │       └── Conexion.java
    └── webapp/              ← Vistas JSP (solo JSTL)
        ├── index.jsp
        ├── registroLibro.jsp
        ├── registroEstudiante.jsp
        ├── registroPrestamo.jsp
        ├── listaLibros.jsp
        ├── listaEstudiantes.jsp
        ├── listaPrestamos.jsp
        └── confirmacion.jsp
```

---

## Funcionalidades implementadas

| Módulo       | Funcionalidad                          |
|--------------|----------------------------------------|
| **Libros**   | Registrar + Listar + Select de categorías dinámico |
| **Estudiantes** | Registrar + Listar                  |
| **Préstamos**| Registrar (con validación de disponibilidad) + Listar + Devolver |

---

## Notas importantes (según rúbrica)

- **Cero scriptlets** en las JSP (`<% %>` no se usa).
- Solo JSTL (`c:forEach`, `c:if`, `c:choose`) + EL (`${objeto.propiedad}`).
- Los DAO son los únicos que ejecutan SQL.
- Los Servlets solo orquestan (reciben parámetros → llenan Beans → llaman DAO → redirigen).
- Bootstrap 5 para el diseño.
- Driver MySQL incluido en `pom.xml`.

---

## Criterios de evaluación cubiertos

| Criterio              | Puntaje | Estado |
|-----------------------|---------|--------|
| Base de datos         | 10      | ✅     |
| JavaBeans             | 25      | ✅     |
| JSTL (sin scriptlets) | 20      | ✅     |
| Formularios y vistas  | 10      | ✅     |
| Conexión JDBC         | 15      | ✅     |
| Presentación          | 10      | —      |

---

**¡Éxito en la entrega y en la presentación oral!**
