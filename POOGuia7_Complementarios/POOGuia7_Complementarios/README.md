# Ejercicios Complementarios - Guía 7 (JDBC)

## Requisitos
1. MySQL / WAMP / XAMPP en ejecución.
2. Ejecutar el script `sql/script_bd.sql` en phpMyAdmin (o cliente MySQL).
3. Usuario por defecto: `root` sin contraseña (ajusta en `Conexion.java` si es necesario).

## Estructura
```
POOGuia7_Complementarios/
├── pom.xml
├── sql/script_bd.sql          ← crear BD + datos de ejemplo
├── src/main/java/com/example/pooguia7/
│   ├── aplicacion/Principal.java
│   ├── conexiones/Conexion.java
│   ├── dao/AlumnoDAO.java
│   ├── dao/MateriaDAO.java
│   ├── modelos/Alumno.java
│   ├── modelos/Materia.java
│   └── validacion/Validador.java
```

## Qué incluye
1. **Base de datos** `academiabdd` con tablas:
   - `alumno`
   - `materia`
   - `alumno_materia` (relación muchos a muchos)

2. **CRUD completo** para `alumno` y `materia`
   - Insertar, Actualizar, Eliminar, Buscar, Listar

3. **Reporte**: listar las materias que cursa un alumno específico elegido por el usuario.

4. **Clase de validación** dedicada (`Validador`) según las notas de la guía.

5. **JOptionPane** usado solo para mensajes de éxito/error (y menús de entrada).

## Cómo ejecutar
1. Ejecuta `sql/script_bd.sql` en MySQL.
2. Abre el proyecto en NetBeans / IntelliJ.
3. Ejecuta la clase `Principal`.
