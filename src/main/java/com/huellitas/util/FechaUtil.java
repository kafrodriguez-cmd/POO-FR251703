package com.huellitas.util;

import java.time.LocalDate;
import java.time.Year;

/**
 * Utilidad centralizada para validaciones de fechas del negocio.
 *
 * Reglas implementadas:
 * 1. Fecha de ingreso / rescate de mascota: no puede ser futura (máximo = hoy).
 * 2. Fecha de adopción / cita / reserva: solo puede ser hoy o futura (dentro del año actual).
 * 3. Ninguna fecha puede pertenecer a un año posterior al año actual del sistema.
 */
public final class FechaUtil {

    private FechaUtil() {}

    /** Año actual del sistema (se detecta dinámicamente). */
    public static int anioActual() {
        return Year.now().getValue();
    }

    /** Fecha de hoy. */
    public static LocalDate hoy() {
        return LocalDate.now();
    }

    /**
     * Valida fecha de ingreso / rescate de una mascota.
     * - No puede ser nula
     * - No puede ser posterior a hoy
     * - No puede ser de un año futuro al actual
     */
    public static String validarFechaIngreso(LocalDate fecha) {
        if (fecha == null) {
            return "La fecha de ingreso/rescate es obligatoria.";
        }
        if (fecha.getYear() > anioActual()) {
            return "La fecha de ingreso no puede pertenecer a un año futuro (" + anioActual() + ").";
        }
        if (fecha.isAfter(hoy())) {
            return "La fecha de ingreso/rescate no puede ser posterior a hoy.";
        }
        return null; // válida
    }

    /**
     * Valida fecha de adopción / cita / reserva.
     * - No puede ser nula
     * - Debe ser hoy o una fecha futura
     * - No puede ser de un año posterior al actual
     */
    public static String validarFechaFutura(LocalDate fecha, String nombreCampo) {
        if (fecha == null) {
            return "La " + nombreCampo + " es obligatoria.";
        }
        if (fecha.getYear() > anioActual()) {
            return "La " + nombreCampo + " no puede pertenecer a un año futuro (" + anioActual() + ").";
        }
        if (fecha.isBefore(hoy())) {
            return "La " + nombreCampo + " solo puede ser hoy o una fecha futura.";
        }
        return null; // válida
    }

    /**
     * Convierte java.sql.Date a LocalDate de forma segura.
     */
    public static LocalDate toLocalDate(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }

    /**
     * Convierte String (yyyy-MM-dd) a LocalDate de forma segura.
     */
    public static LocalDate parse(String fechaStr) {
        if (fechaStr == null || fechaStr.isBlank()) return null;
        try {
            return LocalDate.parse(fechaStr.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
