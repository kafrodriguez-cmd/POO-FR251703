package com.example.pooguia7.validacion;

/**
 * Clase dedicada exclusivamente a validar datos del usuario
 * (según nota de los ejercicios complementarios).
 */
public class Validador {

    private Validador() {
        // utilidad estática
    }

    /** Verifica que el texto no sea null ni vacío (después de trim). */
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /** Verifica que sea un entero positivo (> 0). */
    public static boolean esEnteroPositivo(String valor) {
        if (!esTextoValido(valor)) return false;
        try {
            return Integer.parseInt(valor.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Verifica que sea un entero no negativo (>= 0). */
    public static boolean esEnteroNoNegativo(String valor) {
        if (!esTextoValido(valor)) return false;
        try {
            return Integer.parseInt(valor.trim()) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Edad razonable para un alumno (1-120). */
    public static boolean esEdadValida(String edad) {
        if (!esEnteroPositivo(edad)) return false;
        int e = Integer.parseInt(edad.trim());
        return e >= 1 && e <= 120;
    }

    /** Longitud máxima de un campo de texto. */
    public static boolean longitudMaxima(String texto, int max) {
        return esTextoValido(texto) && texto.trim().length() <= max;
    }
}
