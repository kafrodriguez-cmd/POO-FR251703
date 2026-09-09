package com.example.pooguia7.aplicacion;

import com.example.pooguia7.dao.AlumnoDAO;
import com.example.pooguia7.dao.MateriaDAO;
import com.example.pooguia7.modelos.Alumno;
import com.example.pooguia7.modelos.Materia;
import com.example.pooguia7.validacion.Validador;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Menú principal de los ejercicios complementarios (Guía 7).
 * JOptionPane se usa SOLO para informar éxito o error de operaciones.
 * La captura de datos se hace por consola + validación con la clase Validador.
 */
public class Principal {

    private static final AlumnoDAO alumnoDAO = new AlumnoDAO();
    private static final MateriaDAO materiaDAO = new MateriaDAO();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            String opcion = JOptionPane.showInputDialog(null,
                    "=== MENÚ ACADEMIA ===\n\n"
                    + "1. CRUD Alumnos\n"
                    + "2. CRUD Materias\n"
                    + "3. Reporte: materias de un alumno\n"
                    + "4. Asignar materia a alumno\n"
                    + "5. Salir\n\n"
                    + "Elija una opción:",
                    "Ejercicios Complementarios - Guía 7",
                    JOptionPane.QUESTION_MESSAGE);

            if (opcion == null) {
                salir = true;
                continue;
            }

            switch (opcion.trim()) {
                case "1" -> menuAlumnos();
                case "2" -> menuMaterias();
                case "3" -> reporteMateriasAlumno();
                case "4" -> asignarMateria();
                case "5" -> salir = true;
                default -> JOptionPane.showMessageDialog(null,
                        "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        System.exit(0);
    }

    // ===================== CRUD ALUMNOS =====================
    private static void menuAlumnos() {
        String op = JOptionPane.showInputDialog(null,
                "CRUD ALUMNOS\n\n"
                + "1. Insertar\n"
                + "2. Actualizar\n"
                + "3. Eliminar\n"
                + "4. Buscar por código\n"
                + "5. Listar todos\n"
                + "0. Volver",
                "Alumnos", JOptionPane.QUESTION_MESSAGE);
        if (op == null) return;

        try {
            switch (op.trim()) {
                case "1" -> insertarAlumno();
                case "2" -> actualizarAlumno();
                case "3" -> eliminarAlumno();
                case "4" -> buscarAlumno();
                case "5" -> listarAlumnos();
                case "0" -> { /* volver */ }
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo completar la operación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void insertarAlumno() throws Exception {
        String cod = pedir("Código del alumno (entero positivo):");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        if (alumnoDAO.existe(codigo)) {
            JOptionPane.showMessageDialog(null, "Ya existe un alumno con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = pedir("Nombre:");
        if (!Validador.longitudMaxima(nombre, 80)) {
            JOptionPane.showMessageDialog(null, "Nombre inválido o demasiado largo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String apellido = pedir("Apellido:");
        if (!Validador.longitudMaxima(apellido, 80)) {
            JOptionPane.showMessageDialog(null, "Apellido inválido o demasiado largo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String edad = pedir("Edad:");
        if (!Validador.esEdadValida(edad)) {
            JOptionPane.showMessageDialog(null, "Edad inválida (1-120).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String direccion = pedir("Dirección:");
        if (!Validador.longitudMaxima(direccion, 100)) {
            JOptionPane.showMessageDialog(null, "Dirección inválida o demasiado larga.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Alumno a = new Alumno(codigo, nombre.trim(), apellido.trim(),
                Integer.parseInt(edad.trim()), direccion.trim());
        if (alumnoDAO.insertar(a)) {
            JOptionPane.showMessageDialog(null, "Alumno insertado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo insertar el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void actualizarAlumno() throws Exception {
        String cod = pedir("Código del alumno a actualizar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        Alumno existente = alumnoDAO.buscarPorCodigo(codigo);
        if (existente == null) {
            JOptionPane.showMessageDialog(null, "No existe alumno con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = pedir("Nuevo nombre (" + existente.getNombre() + "):");
        if (!Validador.longitudMaxima(nombre, 80)) {
            JOptionPane.showMessageDialog(null, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String apellido = pedir("Nuevo apellido (" + existente.getApellido() + "):");
        if (!Validador.longitudMaxima(apellido, 80)) {
            JOptionPane.showMessageDialog(null, "Apellido inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String edad = pedir("Nueva edad (" + existente.getEdad() + "):");
        if (!Validador.esEdadValida(edad)) {
            JOptionPane.showMessageDialog(null, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String direccion = pedir("Nueva dirección (" + existente.getDireccion() + "):");
        if (!Validador.longitudMaxima(direccion, 100)) {
            JOptionPane.showMessageDialog(null, "Dirección inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Alumno a = new Alumno(codigo, nombre.trim(), apellido.trim(),
                Integer.parseInt(edad.trim()), direccion.trim());
        if (alumnoDAO.actualizar(a)) {
            JOptionPane.showMessageDialog(null, "Alumno actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarAlumno() throws Exception {
        String cod = pedir("Código del alumno a eliminar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        if (!alumnoDAO.existe(codigo)) {
            JOptionPane.showMessageDialog(null, "No existe alumno con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (alumnoDAO.eliminar(codigo)) {
            JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void buscarAlumno() throws Exception {
        String cod = pedir("Código del alumno a buscar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Alumno a = alumnoDAO.buscarPorCodigo(Integer.parseInt(cod.trim()));
        if (a == null) {
            JOptionPane.showMessageDialog(null, "Alumno no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, a.toString(), "Alumno encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void listarAlumnos() throws Exception {
        List<Alumno> lista = alumnoDAO.listarTodos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay alumnos registrados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("LISTADO DE ALUMNOS\n\n");
        for (Alumno a : lista) {
            sb.append(a.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Alumnos", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== CRUD MATERIAS =====================
    private static void menuMaterias() {
        String op = JOptionPane.showInputDialog(null,
                "CRUD MATERIAS\n\n"
                + "1. Insertar\n"
                + "2. Actualizar\n"
                + "3. Eliminar\n"
                + "4. Buscar por código\n"
                + "5. Listar todas\n"
                + "0. Volver",
                "Materias", JOptionPane.QUESTION_MESSAGE);
        if (op == null) return;

        try {
            switch (op.trim()) {
                case "1" -> insertarMateria();
                case "2" -> actualizarMateria();
                case "3" -> eliminarMateria();
                case "4" -> buscarMateria();
                case "5" -> listarMaterias();
                case "0" -> { /* volver */ }
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo completar la operación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void insertarMateria() throws Exception {
        String cod = pedir("Código de la materia (entero positivo):");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        if (materiaDAO.existe(codigo)) {
            JOptionPane.showMessageDialog(null, "Ya existe una materia con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nombre = pedir("Nombre de la materia (máx 25):");
        if (!Validador.longitudMaxima(nombre, 25)) {
            JOptionPane.showMessageDialog(null, "Nombre inválido o demasiado largo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String desc = pedir("Descripción (máx 100):");
        if (!Validador.longitudMaxima(desc, 100)) {
            JOptionPane.showMessageDialog(null, "Descripción inválida o demasiado larga.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Materia m = new Materia(codigo, nombre.trim(), desc.trim());
        if (materiaDAO.insertar(m)) {
            JOptionPane.showMessageDialog(null, "Materia insertada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo insertar la materia.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void actualizarMateria() throws Exception {
        String cod = pedir("Código de la materia a actualizar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        Materia existente = materiaDAO.buscarPorCodigo(codigo);
        if (existente == null) {
            JOptionPane.showMessageDialog(null, "No existe materia con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nombre = pedir("Nuevo nombre (" + existente.getNombre() + "):");
        if (!Validador.longitudMaxima(nombre, 25)) {
            JOptionPane.showMessageDialog(null, "Nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String desc = pedir("Nueva descripción (" + existente.getDescripcion() + "):");
        if (!Validador.longitudMaxima(desc, 100)) {
            JOptionPane.showMessageDialog(null, "Descripción inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Materia m = new Materia(codigo, nombre.trim(), desc.trim());
        if (materiaDAO.actualizar(m)) {
            JOptionPane.showMessageDialog(null, "Materia actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void eliminarMateria() throws Exception {
        String cod = pedir("Código de la materia a eliminar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(cod.trim());
        if (!materiaDAO.existe(codigo)) {
            JOptionPane.showMessageDialog(null, "No existe materia con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (materiaDAO.eliminar(codigo)) {
            JOptionPane.showMessageDialog(null, "Materia eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void buscarMateria() throws Exception {
        String cod = pedir("Código de la materia a buscar:");
        if (!Validador.esEnteroPositivo(cod)) {
            JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Materia m = materiaDAO.buscarPorCodigo(Integer.parseInt(cod.trim()));
        if (m == null) {
            JOptionPane.showMessageDialog(null, "Materia no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, m.toString(), "Materia encontrada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void listarMaterias() throws Exception {
        List<Materia> lista = materiaDAO.listarTodos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay materias registradas.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("LISTADO DE MATERIAS\n\n");
        for (Materia m : lista) {
            sb.append(m.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Materias", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================== REPORTE =====================
    private static void reporteMateriasAlumno() {
        try {
            String cod = pedir("Código del alumno:");
            if (!Validador.esEnteroPositivo(cod)) {
                JOptionPane.showMessageDialog(null, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int codigo = Integer.parseInt(cod.trim());
            Alumno a = alumnoDAO.buscarPorCodigo(codigo);
            if (a == null) {
                JOptionPane.showMessageDialog(null, "No existe alumno con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Materia> materias = materiaDAO.materiasDeAlumno(codigo);
            if (materias.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El alumno " + a.getNombre() + " " + a.getApellido() + " no tiene materias asignadas.",
                        "Reporte", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Materias que cursa:\n")
              .append(a.getNombre()).append(" ").append(a.getApellido())
              .append(" (cód. ").append(codigo).append(")\n\n");
            for (Materia m : materias) {
                sb.append("• ").append(m.getNombre())
                  .append(" - ").append(m.getDescripcion()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Reporte de materias", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo generar el reporte:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== ASIGNAR MATERIA =====================
    private static void asignarMateria() {
        try {
            String codAl = pedir("Código del alumno:");
            if (!Validador.esEnteroPositivo(codAl)) {
                JOptionPane.showMessageDialog(null, "Código de alumno inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int codAlumno = Integer.parseInt(codAl.trim());
            if (!alumnoDAO.existe(codAlumno)) {
                JOptionPane.showMessageDialog(null, "No existe ese alumno.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String codMat = pedir("Código de la materia:");
            if (!Validador.esEnteroPositivo(codMat)) {
                JOptionPane.showMessageDialog(null, "Código de materia inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int codMateria = Integer.parseInt(codMat.trim());
            if (!materiaDAO.existe(codMateria)) {
                JOptionPane.showMessageDialog(null, "No existe esa materia.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (materiaDAO.asignarMateria(codAlumno, codMateria)) {
                JOptionPane.showMessageDialog(null, "Materia asignada correctamente al alumno.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo asignar (¿ya estaba asignada?).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo asignar la materia:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== UTILIDAD =====================
    private static String pedir(String mensaje) {
        return JOptionPane.showInputDialog(null, mensaje, "Entrada de datos", JOptionPane.QUESTION_MESSAGE);
    }
}
