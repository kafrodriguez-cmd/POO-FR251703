# Centro de Adopción de Mascotas “Huellitas Felices”
## Documento del Proyecto de Cátedra – POO404

**Universidad Don Bosco**  
**Facultad de Ingeniería – Escuela de Computación**  
**Materia:** Programación Orientada a Objetos (POO404) – Ciclo II  
**Tipo de entregable:** Sistema web completo bajo arquitectura MVC  

---

### 1. Descripción del problema y dominio elegido

En El Salvador (y en general en Centroamérica) existen numerosos animales en situación de abandono. Los centros de adopción tradicionales suelen gestionar la información de forma manual (cuadernos, hojas de Excel sueltas), lo que genera:

- Duplicidad de datos  
- Pérdida de historial de adopciones  
- Dificultad para generar reportes  
- Falta de control de acceso  

**Huellitas Felices** es un sistema web que digitaliza y centraliza la gestión de un centro de adopción de mascotas. Permite registrar mascotas disponibles, adoptantes, procesos de adopción, controlar el estado de cada animal y generar reportes con información combinada de varias tablas.

El sistema está orientado a **personal administrativo y empleados** del centro (no es un portal público de adopción).

---

### 2. Diseño de base de datos

#### 2.1 Entidades principales

| Entidad     | Descripción                                      | Tipo de relación                          |
|-------------|--------------------------------------------------|-------------------------------------------|
| Usuario     | Personal que inicia sesión (Admin / Empleado)    | 1 : N con Adopción                        |
| Mascota     | Animal disponible o en proceso de adopción       | 1 : N con Adopción                        |
| Adoptante   | Persona interesada en adoptar                    | 1 : N con Adopción                        |
| Adopcion    | Registro del proceso de adopción                  | N : 1 Mascota + N : 1 Adoptante           |
| Vacuna      | Catálogo de vacunas                              | N : M con Mascota (tabla intermedia)      |

#### 2.2 Relaciones

- **Uno a muchos:** Un Adoptante puede tener varias Adopciones.  
- **Uno a muchos:** Una Mascota puede tener varias Adopciones (histórico).  
- **Muchos a muchos:** Una Mascota puede recibir varias Vacunas y una Vacuna puede aplicarse a muchas Mascotas (tabla `mascota_vacuna`).

#### 2.3 Justificación del diseño

Se eligió un modelo normalizado en 3FN:

- Evita redundancia (los datos del adoptante y de la mascota no se repiten en cada adopción).  
- Permite consultas con JOIN de forma eficiente.  
- Facilita la extensión futura (historial médico, donaciones, voluntarios, etc.).  
- La tabla `usuario` separa la autenticación de los datos de negocio.

---

### 3. Arquitectura MVC implementada

```
Cliente (Navegador)
        │
        ▼
┌───────────────────┐
│   VISTA (JSP)     │  ← Bootstrap 5 + JSTL  (presentación)
│  /WEB-INF/vistas  │
└─────────┬─────────┘
          │ request / response
          ▼
┌───────────────────┐
│ CONTROLADOR       │  ← Servlets (@WebServlet)
│  (paquete         │     LoginServlet, MascotaServlet,
│   controlador)    │     AdoptanteServlet, AdopcionServlet,
└─────────┬─────────┘     DashboardServlet
          │
          ▼
┌───────────────────┐
│    MODELO         │
│  JavaBeans        │  ← Usuario, Mascota, Adoptante, Adopcion
│  + DAOs (JDBC)    │  ← UsuarioDAO, MascotaDAO, ...
│  + Conexion       │  ← Clase centralizada de conexión
└─────────┬─────────┘
          │
          ▼
┌───────────────────┐
│   MySQL           │
│ huellitas_felices │
└───────────────────┘
```

**Flujo típico de una petición:**

1. El usuario solicita `/mascotas?accion=listar`.  
2. El contenedor Tomcat invoca `MascotaServlet.doGet()`.  
3. El Servlet verifica la sesión (`HttpSession`).  
4. Llama a `MascotaDAO.listarTodas()`.  
5. El DAO usa `Conexion.getConnection()` y ejecuta el SQL.  
6. Se devuelve una `List<Mascota>`.  
7. El Servlet coloca la lista en el request y hace `forward` a `lista.jsp`.  
8. La JSP (con JSTL `<c:forEach>`) genera el HTML de la tabla.

---

### 4. Funcionalidades implementadas

| Requisito de la guía                          | Estado | Cómo se cumple                                      |
|-----------------------------------------------|--------|-----------------------------------------------------|
| CRUD completo por entidad principal           | ✅     | Mascota, Adoptante, Adopción                         |
| Formulario de registro con validación         | ✅     | Campos obligatorios validados en Servlet            |
| Listar con `<c:forEach>`                      | ✅     | Todas las tablas de listado                         |
| Formulario de edición precargado              | ✅     | Parámetro `accion=editar&id=`                       |
| Eliminar con confirmación                     | ✅     | `confirm()` en JavaScript                           |
| Buscar / Filtrar                              | ✅     | Mascotas (nombre/especie/estado) y Adoptantes       |
| Autenticación + HttpSession                   | ✅     | LoginServlet + verificación en todos los Servlets   |
| Consulta con JOIN                             | ✅     | `AdopcionDAO.listarTodasConDetalle()` + vista reporte |
| Mensajes de éxito / error                     | ✅     | Flash messages en sesión                            |
| JavaBeans con convenciones                    | ✅     | Atributos privados, constructor vacío, getters/setters |
| Conexión centralizada                         | ✅     | Clase `com.huellitas.util.Conexion`                 |
| Interfaz responsive                           | ✅     | Bootstrap 5                                         |

---

### 5. Credenciales de prueba

| Usuario    | Contraseña | Rol      |
|------------|------------|----------|
| admin      | admin123   | ADMIN    |
| empleado   | emp123     | EMPLEADO |

---

### 6. Instrucciones de despliegue (NetBeans + Tomcat)

1. Crear la base de datos ejecutando el script `sql/huellitas_felices.sql` en MySQL/phpMyAdmin.  
2. Abrir el proyecto en NetBeans (File → Open Project).  
3. Verificar en `Conexion.java` el usuario/contraseña de MySQL.  
4. Agregar el servidor Apache Tomcat 10+ en NetBeans.  
5. Clic derecho sobre el proyecto → Run.  
6. Acceder a `http://localhost:8080/HuellitasFelices/`.

---

### 7. Conclusiones

El sistema **Huellitas Felices** demuestra la aplicación práctica de los conceptos de Programación Orientada a Objetos vistos a lo largo del ciclo:

- **Encapsulación** → atributos privados + getters/setters en los JavaBeans.  
- **Abstracción** → DAOs ocultan el detalle del acceso a datos.  
- **Herencia** → todos los Servlets extienden `HttpServlet`.  
- **Polimorfismo** → uso de interfaces de la API de Servlets y colecciones.  

Además se integran las tecnologías de las últimas guías de laboratorio (JDBC, Servlets, JSP, JSTL, sesiones y Tomcat) en un producto funcional y documentado.
