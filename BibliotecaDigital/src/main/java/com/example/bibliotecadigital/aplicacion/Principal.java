package com.example.bibliotecadigital.aplicacion;

import com.example.bibliotecadigital.vista.frmBiblioteca;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar la aplicación de Biblioteca Digital.
 */
public class Principal {

    public static void main(String[] args) {
        // Look and Feel del sistema (más moderno)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, se usa el Look and Feel por defecto
        }

        SwingUtilities.invokeLater(() -> {
            frmBiblioteca ventana = new frmBiblioteca();
            ventana.setVisible(true);
        });
    }
}
