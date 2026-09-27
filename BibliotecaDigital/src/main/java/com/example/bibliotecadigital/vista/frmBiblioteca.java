package com.example.bibliotecadigital.vista;

import com.example.bibliotecadigital.beans.AutorBeans;
import com.example.bibliotecadigital.beans.CategoriaBeans;
import com.example.bibliotecadigital.beans.LibroBeans;
import com.example.bibliotecadigital.datos.AutorDatos;
import com.example.bibliotecadigital.datos.CategoriaDatos;
import com.example.bibliotecadigital.datos.LibroDatos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Formulario principal de la Biblioteca Digital.
 * Interfaz gráfica con JFrame + JDBC (CRUD completo).
 * Sigue la arquitectura y estilo de las Guías 6 y 8.
 */
public class frmBiblioteca extends JFrame {

    // Componentes de entrada
    private JTextField txtTitulo;
    private JTextField txtAnio;
    private JComboBox<AutorBeans> cmbAutor;
    private JComboBox<CategoriaBeans> cmbCategoria;

    // Botones
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnFiltrar;
    private JButton btnVerTodos;

    // Tabla
    private JTable tblLibros;
    private DefaultTableModel modeloTabla;

    // Capas de datos
    private final LibroDatos libroDatos = new LibroDatos();
    private final AutorDatos autorDatos = new AutorDatos();
    private final CategoriaDatos categoriaDatos = new CategoriaDatos();

    // Variable de control para edición
    private int idLibroSeleccionado = -1;

    public frmBiblioteca() {
        initComponents();
        configurarVentana();
        cargarCombos();
        cargarTabla();
    }

    private void configurarVentana() {
        setTitle("Biblioteca Digital - Administración de Libros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 550));
    }

    private void initComponents() {
        // ===== PANEL PRINCIPAL =====
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(245, 245, 250));

        // ===== TÍTULO =====
        JLabel lblTituloPrincipal = new JLabel("Administración de Biblioteca Digital");
        lblTituloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloPrincipal.setForeground(new Color(30, 60, 110));
        lblTituloPrincipal.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        panelPrincipal.add(lblTituloPrincipal, BorderLayout.NORTH);

        // ===== PANEL CENTRAL (Formulario + Tabla) =====
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setOpaque(false);

        // ----- Formulario -----
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 140, 200), 1),
                "Datos del Libro",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(30, 60, 110)
        ));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setPreferredSize(new Dimension(0, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblTitulo = new JLabel("Título del libro:");
        lblTitulo.setFont(labelFont);
        panelFormulario.add(lblTitulo, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTitulo = new JTextField(25);
        txtTitulo.setFont(fieldFont);
        panelFormulario.add(txtTitulo, gbc);

        // Año
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblAnio = new JLabel("Año de publicación:");
        lblAnio.setFont(labelFont);
        panelFormulario.add(lblAnio, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        txtAnio = new JTextField(10);
        txtAnio.setFont(fieldFont);
        panelFormulario.add(txtAnio, gbc);

        // Autor
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(labelFont);
        panelFormulario.add(lblAutor, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbAutor = new JComboBox<>();
        cmbAutor.setFont(fieldFont);
        panelFormulario.add(cmbAutor, gbc);

        // Categoría
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(labelFont);
        panelFormulario.add(lblCategoria, gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setFont(fieldFont);
        panelFormulario.add(cmbCategoria, gbc);

        panelCentro.add(panelFormulario, BorderLayout.NORTH);

        // ----- Tabla -----
        String[] columnas = {"ID", "Título", "Año", "Autor", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Solo lectura
            }
        };
        tblLibros = new JTable(modeloTabla);
        tblLibros.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblLibros.setRowHeight(24);
        tblLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblLibros.getTableHeader().setBackground(new Color(60, 100, 160));
        tblLibros.getTableHeader().setForeground(Color.BLACK);
        tblLibros.setSelectionBackground(new Color(180, 210, 250));

        // Ocultar columna ID (se usa internamente)
        tblLibros.getColumnModel().getColumn(0).setMinWidth(0);
        tblLibros.getColumnModel().getColumn(0).setMaxWidth(0);
        tblLibros.getColumnModel().getColumn(0).setWidth(0);

        // Click en la tabla → cargar datos en el formulario
        tblLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblLibros.getSelectedRow();
                if (fila >= 0) {
                    idLibroSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                    txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtAnio.setText(modeloTabla.getValueAt(fila, 2).toString());

                    // Seleccionar autor en el combo
                    String nombreAutor = modeloTabla.getValueAt(fila, 3).toString();
                    seleccionarAutorEnCombo(nombreAutor);

                    // Seleccionar categoría en el combo
                    String nombreCat = modeloTabla.getValueAt(fila, 4).toString();
                    seleccionarCategoriaEnCombo(nombreCat);
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tblLibros);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 140, 200), 1),
                "Libros Registrados",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(30, 60, 110)
        ));
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ===== PANEL DE BOTONES =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        panelBotones.setOpaque(false);

        btnGuardar = crearBoton("Guardar", new Color(40, 140, 70));
        btnEditar = crearBoton("Editar", new Color(40, 100, 180));
        btnEliminar = crearBoton("Eliminar", new Color(180, 50, 50));
        btnLimpiar = crearBoton("Limpiar", new Color(120, 120, 120));
        btnFiltrar = crearBoton("Filtrar", new Color(150, 100, 30));
        btnVerTodos = crearBoton("Ver Todos", new Color(80, 80, 140));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnVerTodos);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Eventos de botones
        btnGuardar.addActionListener(e -> guardarLibro());
        btnEditar.addActionListener(e -> editarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnFiltrar.addActionListener(e -> filtrarLibros());
        btnVerTodos.addActionListener(e -> cargarTabla());

        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(colorFondo);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(110, 35));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    // ==================== MÉTODOS DE CARGA ====================

    private void cargarCombos() {
        // Cargar autores
        DefaultComboBoxModel<AutorBeans> modeloAutor = new DefaultComboBoxModel<>();
        List<AutorBeans> autores = autorDatos.listarTodos();
        for (AutorBeans a : autores) {
            modeloAutor.addElement(a);
        }
        cmbAutor.setModel(modeloAutor);

        // Cargar categorías
        DefaultComboBoxModel<CategoriaBeans> modeloCat = new DefaultComboBoxModel<>();
        List<CategoriaBeans> categorias = categoriaDatos.listarTodas();
        for (CategoriaBeans c : categorias) {
            modeloCat.addElement(c);
        }
        cmbCategoria.setModel(modeloCat);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<LibroBeans> lista = libroDatos.listarTodos();
        for (LibroBeans l : lista) {
            modeloTabla.addRow(new Object[]{
                l.getIdLibro(),
                l.getTitulo(),
                l.getAnioPublicacion(),
                l.getNombreAutor(),
                l.getNombreCategoria()
            });
        }
    }

    private void seleccionarAutorEnCombo(String nombre) {
        for (int i = 0; i < cmbAutor.getItemCount(); i++) {
            AutorBeans a = cmbAutor.getItemAt(i);
            if (a.getNombre().equals(nombre)) {
                cmbAutor.setSelectedIndex(i);
                break;
            }
        }
    }

    private void seleccionarCategoriaEnCombo(String nombre) {
        for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
            CategoriaBeans c = cmbCategoria.getItemAt(i);
            if (c.getNombreCategoria().equals(nombre)) {
                cmbCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    // ==================== CRUD ====================

    private void guardarLibro() {
        if (!validarCampos()) {
            return;
        }

        try {
            LibroBeans libro = new LibroBeans();
            libro.setTitulo(txtTitulo.getText().trim());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));

            AutorBeans autorSel = (AutorBeans) cmbAutor.getSelectedItem();
            CategoriaBeans catSel = (CategoriaBeans) cmbCategoria.getSelectedItem();

            if (autorSel == null || catSel == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un Autor y una Categoría.\nVerifique que existan registros en la base de datos.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            libro.setIdAutor(autorSel.getIdAutor());
            libro.setIdCategoria(catSel.getIdCategoria());

            boolean exito = libroDatos.insertar(libro);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Libro guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar el libro.\nVerifique la conexión a la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarLibro() {
        if (idLibroSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }

        try {
            LibroBeans libro = new LibroBeans();
            libro.setIdLibro(idLibroSeleccionado);
            libro.setTitulo(txtTitulo.getText().trim());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));

            AutorBeans autorSel = (AutorBeans) cmbAutor.getSelectedItem();
            CategoriaBeans catSel = (CategoriaBeans) cmbCategoria.getSelectedItem();

            if (autorSel == null || catSel == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Autor y Categoría.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            libro.setIdAutor(autorSel.getIdAutor());
            libro.setIdCategoria(catSel.getIdCategoria());

            boolean exito = libroDatos.actualizar(libro);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        if (idLibroSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el libro seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = libroDatos.eliminar(idLibroSeleccionado);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filtrarLibros() {
        String[] opciones = {"Por Autor", "Por Categoría"};
        int seleccion = JOptionPane.showOptionDialog(this,
                "¿Cómo desea filtrar los libros?",
                "Filtrar",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (seleccion == 0) { // Por Autor
            AutorBeans autor = (AutorBeans) cmbAutor.getSelectedItem();
            if (autor == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un autor en el combo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            modeloTabla.setRowCount(0);
            List<LibroBeans> lista = libroDatos.listarPorAutor(autor.getIdAutor());
            for (LibroBeans l : lista) {
                modeloTabla.addRow(new Object[]{
                    l.getIdLibro(), l.getTitulo(), l.getAnioPublicacion(),
                    l.getNombreAutor(), l.getNombreCategoria()
                });
            }
        } else if (seleccion == 1) { // Por Categoría
            CategoriaBeans cat = (CategoriaBeans) cmbCategoria.getSelectedItem();
            if (cat == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una categoría en el combo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            modeloTabla.setRowCount(0);
            List<LibroBeans> lista = libroDatos.listarPorCategoria(cat.getIdCategoria());
            for (LibroBeans l : lista) {
                modeloTabla.addRow(new Object[]{
                    l.getIdLibro(), l.getTitulo(), l.getAnioPublicacion(),
                    l.getNombreAutor(), l.getNombreCategoria()
                });
            }
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAnio.setText("");
        if (cmbAutor.getItemCount() > 0) {
            cmbAutor.setSelectedIndex(0);
        }
        if (cmbCategoria.getItemCount() > 0) {
            cmbCategoria.setSelectedIndex(0);
        }
        idLibroSeleccionado = -1;
        tblLibros.clearSelection();
        txtTitulo.requestFocus();
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título del libro es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }
        if (txtAnio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El año de publicación es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (anio < 1000 || anio > 2100) {
                JOptionPane.showMessageDialog(this, "Ingrese un año válido (entre 1000 y 2100).", "Validación", JOptionPane.WARNING_MESSAGE);
                txtAnio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número entero.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }
        return true;
    }
}
