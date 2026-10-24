package com.huellitas.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Método para encriptar la contraseña (genera un salt automáticamente y aplica hash)
    public static String hashPassword(String passwordPlain) {
        return BCrypt.hashpw(passwordPlain, BCrypt.gensalt());
    }

    // Método para verificar si una contraseña ingresada coincide con el hash almacenado en la BD
    public static boolean checkPassword(String passwordPlain, String hashedPassword) {
        if (hashedPassword == null || 
            (!hashedPassword.startsWith("$2a$") && 
             !hashedPassword.startsWith("$2b$") && 
             !hashedPassword.startsWith("$2y$"))) {
            return false; // Validación de seguridad para formatos BCrypt válidos
        }
        return BCrypt.checkpw(passwordPlain, hashedPassword);
    }
}