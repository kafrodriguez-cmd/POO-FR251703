# Biblioteca Digital - Desafío Práctico #02 (POO404)

Sistema de escritorio para administrar una biblioteca digital usando **Java Swing (JFrame)** + **JDBC** + **MySQL**.

Compatible con **Apache NetBeans IDE 28** (y versiones anteriores).

---

## Estructura del proyecto (arquitectura pedida)

```
BibliotecaDigital/
├── pom.xml
├── script_biblioteca_db.sql          ← Script para crear la BD
├── README.md
└── src/main/java/com/example/bibliotecadigital/
    ├── aplicacion/
    │   └── Principal.java            ← Clase main
    ├── beans/
    │   ├── AutorBeans.java
    │   ├── CategoriaBeans.java
    │   └── LibroBeans.java
    ├── datos/
    │   ├── AutorDatos.java
    │   ├── CategoriaDatos.java
    │   └── LibroDatos.java           ← CRUD completo
    ├── util/
    │   └── Conexion.java             ← Conexión JDBC
    └── vista/
        └── frmBiblioteca.java        ← Interfaz gráfica (JFrame)
```

---

## Cómo importar en NetBeans IDE 28

1. Abre **Apache NetBeans**.
2. `File → Open Project...`
3. Selecciona la carpeta **BibliotecaDigital** (la que contiene el `pom.xml`).
4. NetBeans detectará que es un proyecto Maven y lo abrirá.
5. Espera a que descargue las dependencias (MySQL Connector).

---

## Configurar la base de datos (cuando estés listo)

1. Abre **phpMyAdmin** (WampServer / XAMPP) o MySQL Workbench.
2. Ejecuta el archivo `script_biblioteca_db.sql` completo.
3. Si tu MySQL tiene contraseña, edita el archivo:
   - `src/main/java/com/example/bibliotecadigital/util/Conexion.java`
   - Cambia `JDBC_PASS = "";` por tu contraseña.

---

## Ejecutar la aplicación

### Desde NetBeans:
- Clic derecho en el proyecto → **Run**
- O abre `Principal.java` y presiona el botón Run (▶)

### Desde terminal (opcional):
```bash
cd BibliotecaDigital
mvn clean compile exec:java
```

---

## Funcionalidades incluidas

- ✅ Registrar libros (Guardar)
- ✅ Editar libros (seleccionar de la tabla → modificar → Editar)
- ✅ Eliminar libros (con confirmación)
- ✅ Limpiar campos
- ✅ Tabla (JTable) con todos los libros (JOIN con autor y categoría)
- ✅ ComboBox de Autores y Categorías (cargados desde BD)
- ✅ Filtrar por Autor o por Categoría
- ✅ Validaciones y mensajes con JOptionPane
- ✅ PreparedStatement en todas las consultas
- ✅ DefaultTableModel y DefaultComboBoxModel
- ✅ Encapsulamiento completo (private + getters/setters)
- ✅ Arquitectura por capas (util / beans / datos / vista)

---

## Notas importantes

- Mientras la base de datos no esté creada, la aplicación abre la ventana pero los combos y la tabla estarán vacíos (no se cae).
- Una vez ejecutado el script SQL, reinicia la aplicación y todo funcionará.
- El proyecto usa Java 17 (compatible con NetBeans 28).

¡Éxito con el desafío!
