/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.javajframe.interfaceswing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

/**
 * PARTE 4 de la Guía - Demostración de uso de la clase JRadioButton
 * Formulario que permite seleccionar una imagen mediante botones de radio.
 *
 * @author kmjh1
 */
public class frmBotonesRadio extends JFrame {

    private JPanel pnlImagenes;
    private JLabel lblTitulo;
    private JLabel lblImagen;
    private JRadioButton rbtOpcion1;
    private JRadioButton rbtOpcion2;
    private JRadioButton rbtOpcion3;
    private ButtonGroup buttonGroup1;

    /**
     * Constructor del formulario.
     * @param title Título de la ventana
     */
    public frmBotonesRadio(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        this.setContentPane(pnlImagenes);
        this.setMinimumSize(new Dimension(400, 400));
        this.setLocationRelativeTo(getParent());
        agregarListeners();
    }

    /**
     * Inicializa los componentes de la interfaz de forma programática
     * (equivalente a lo que haría el diseñador visual de NetBeans/IntelliJ).
     */
    private void initComponents() {
        // Panel principal
        pnlImagenes = new JPanel();
        pnlImagenes.setLayout(new GridBagLayout());
        pnlImagenes.setName("pnlImagenes");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);

        // Título
        lblTitulo = new JLabel("Seleccione una imagen");
        lblTitulo.setFont(new Font("Segoe Print", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlImagenes.add(lblTitulo, gbc);

        // Grupo de botones de radio
        buttonGroup1 = new ButtonGroup();

        // Radio Button Opción 1
        rbtOpcion1 = new JRadioButton("Opcion 1");
        rbtOpcion1.setName("rbtOpcion1");
        rbtOpcion1.setFont(new Font("Segoe Print", Font.PLAIN, 18));
        buttonGroup1.add(rbtOpcion1);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        pnlImagenes.add(rbtOpcion1, gbc);

        // Label de imagen (ocupará varias filas)
        lblImagen = new JLabel();
        lblImagen.setName("lblImagen");
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        // Imagen inicial (question / placeholder). Si no existe question-icon usamos man.png
        try {
            java.net.URL iconUrl = getClass().getResource("/com/example/javajframe/interfaceswing/recursos/man.png");
            if (iconUrl != null) {
                lblImagen.setIcon(new ImageIcon(iconUrl));
            }
        } catch (Exception ex) {
            lblImagen.setText("(sin imagen)");
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 3;          // ocupa 3 filas verticales
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        pnlImagenes.add(lblImagen, gbc);

        // Radio Button Opción 2
        rbtOpcion2 = new JRadioButton("Opcion 2");
        rbtOpcion2.setName("rbtOpcion2");
        rbtOpcion2.setFont(new Font("Segoe Print", Font.PLAIN, 18));
        buttonGroup1.add(rbtOpcion2);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        pnlImagenes.add(rbtOpcion2, gbc);

        // Radio Button Opción 3
        rbtOpcion3 = new JRadioButton("Opcion 3");
        rbtOpcion3.setName("rbtOpcion3");
        rbtOpcion3.setFont(new Font("Segoe Print", Font.PLAIN, 18));
        buttonGroup1.add(rbtOpcion3);
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlImagenes.add(rbtOpcion3, gbc);

        // Espacio inferior
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        pnlImagenes.add(new JLabel(" "), gbc);
    }

    /**
     * Agrega los ActionListener a cada JRadioButton según la guía (pasos 69-70).
     */
    private void agregarListeners() {
        rbtOpcion1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblImagen.setIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/com/example/javajframe/interfaceswing/recursos/img1.jpeg")));
            }
        });

        rbtOpcion2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblImagen.setIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/com/example/javajframe/interfaceswing/recursos/img2.jpeg")));
            }
        });

        rbtOpcion3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon imagen = new ImageIcon(
                        getClass().getResource("/com/example/javajframe/interfaceswing/recursos/img3.jpeg"));
                lblImagen.setIcon(imagen);
            }
        });
    }
}
