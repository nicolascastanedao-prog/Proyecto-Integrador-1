import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class InterfazGrafica extends JFrame {

    // ── Colores ──────────────────────────────────────────────
    private static final Color AZUL_OSCURO  = new Color(20, 50, 90);
    private static final Color AZUL_MEDIO   = new Color(30, 90, 160);
    private static final Color AZUL_CLARO   = new Color(210, 230, 255);
    private static final Color VERDE        = new Color(34, 139, 80);
    private static final Color AMARILLO     = new Color(200, 140, 0);
    private static final Color ROJO         = new Color(180, 30, 30);
    private static final Color BLANCO       = Color.WHITE;
    private static final Color GRIS_FONDO   = new Color(245, 247, 252);

    // ── Pestaña Pacientes ────────────────────────────────────
    private JTable tablaPacientes;
    private DefaultTableModel modeloPacientes;
    private JTextField txtNombre, txtEdad, txtPeso, txtEstatura, txtAntecedentes;
    private JButton btnGuardarPaciente, btnEliminarPaciente, btnLimpiarPaciente;

    // ── Pestaña Mediciones ───────────────────────────────────
    private JComboBox<Paciente> comboPaciente;
    private JTextField txtPresionS, txtPresionD, txtGlucosa, txtFrecuencia;
    private JLabel lblRiesgo;
    private JButton btnGuardarMedicion;

    // ── Pestaña Historial ────────────────────────────────────
    private JComboBox<Paciente> comboHistorial;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;

    private int pacienteSeleccionadoId = -1;

    // ════════════════════════════════════════════════════════
    public InterfazGrafica() {
        setTitle("Sistema de Monitoreo ECNT");
        setSize(820, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(GRIS_FONDO);

        JPanel header = crearHeader();
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(GRIS_FONDO);
        tabs.addTab("👤 Pacientes",   crearPestanaPacientes());
        tabs.addTab("📊 Mediciones",  crearPestanaMediciones());
        tabs.addTab("📋 Historial",   crearPestanaHistorial());

        add(header, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);

        cargarPacientes();
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AZUL_OSCURO);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Sistema de Monitoreo ECNT");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(BLANCO);
        JLabel sub = new JLabel("Enfermedades Crónicas No Transmisibles");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(180, 200, 230));
        JPanel textos = new JPanel(new GridLayout(2,1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(sub);
        p.add(textos, BorderLayout.WEST);
        return p;
    }

    // ════════════════════════════════════════════════════════
    // PESTAÑA 1: PACIENTES
    // ════════════════════════════════════════════════════════
    private JPanel crearPestanaPacientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BLANCO);
        form.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AZUL_MEDIO, 1),
            "Datos del Paciente", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), AZUL_MEDIO));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre       = new JTextField(20);
        txtEdad         = new JTextField(5);
        txtPeso         = new JTextField(5);
        txtEstatura     = new JTextField(5);
        txtAntecedentes = new JTextField(20);

        agregarCampo(form, gbc, "Nombre:",          txtNombre,       0, 0, 3);
        agregarCampo(form, gbc, "Edad:",             txtEdad,         0, 1, 1);
        agregarCampo(form, gbc, "Peso (kg):",        txtPeso,         2, 1, 1);
        agregarCampo(form, gbc, "Estatura (m):",     txtEstatura,     4, 1, 1);
        agregarCampo(form, gbc, "Antecedentes:",     txtAntecedentes, 0, 2, 5);

        // Botones
        btnGuardarPaciente  = boton("💾 Guardar",  AZUL_MEDIO);
        btnEliminarPaciente = boton("🗑 Eliminar", ROJO);
        btnLimpiarPaciente  = boton("✖ Limpiar",  new Color(100,100,100));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botones.setOpaque(false);
        botones.add(btnLimpiarPaciente);
        botones.add(btnEliminarPaciente);
        botones.add(btnGuardarPaciente);

        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=6;
        form.add(botones, gbc);

        // Tabla
        modeloPacientes = new DefaultTableModel(
            new String[]{"ID","Nombre","Edad","Peso","Estatura","Antecedentes"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPacientes = crearTabla(modeloPacientes);
        tablaPacientes.getColumnModel().getColumn(0).setMaxWidth(50);

        tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarPacienteSeleccionado();
        });

        btnGuardarPaciente.addActionListener(e  -> guardarPaciente());
        btnEliminarPaciente.addActionListener(e -> eliminarPaciente());
        btnLimpiarPaciente.addActionListener(e  -> limpiarFormPaciente());

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);
        return panel;
    }

    private void guardarPaciente() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) { mostrarError("El nombre es obligatorio."); return; }
            int    edad      = Integer.parseInt(txtEdad.getText().trim());
            double peso      = Double.parseDouble(txtPeso.getText().trim());
            double estatura  = Double.parseDouble(txtEstatura.getText().trim());
            String antec     = txtAntecedentes.getText().trim();

            if (pacienteSeleccionadoId == -1) {
                Paciente p = new Paciente(nombre, edad, peso, estatura, antec);
                if (PacienteDAO.insertar(p)) { mostrarInfo("Paciente guardado."); }
            } else {
                Paciente p = new Paciente(pacienteSeleccionadoId, nombre, edad, peso, estatura, antec);
                if (PacienteDAO.actualizar(p)) { mostrarInfo("Paciente actualizado."); }
            }
            limpiarFormPaciente();
            cargarPacientes();
        } catch (NumberFormatException ex) {
            mostrarError("Edad, Peso y Estatura deben ser números válidos.");
        }
    }

    private void eliminarPaciente() {
        if (pacienteSeleccionadoId == -1) { mostrarError("Seleccione un paciente."); return; }
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Eliminar este paciente y todas sus mediciones?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            PacienteDAO.eliminar(pacienteSeleccionadoId);
            limpiarFormPaciente();
            cargarPacientes();
        }
    }

    private void cargarPacienteSeleccionado() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) return;
        pacienteSeleccionadoId = (int) modeloPacientes.getValueAt(fila, 0);
        txtNombre.setText((String)  modeloPacientes.getValueAt(fila, 1));
        txtEdad.setText(String.valueOf(modeloPacientes.getValueAt(fila, 2)));
        txtPeso.setText(String.valueOf(modeloPacientes.getValueAt(fila, 3)));
        txtEstatura.setText(String.valueOf(modeloPacientes.getValueAt(fila, 4)));
        txtAntecedentes.setText((String) modeloPacientes.getValueAt(fila, 5));
        btnGuardarPaciente.setText("✏ Actualizar");
    }

    private void limpiarFormPaciente() {
        pacienteSeleccionadoId = -1;
        txtNombre.setText(""); txtEdad.setText(""); txtPeso.setText("");
        txtEstatura.setText(""); txtAntecedentes.setText("");
        tablaPacientes.clearSelection();
        btnGuardarPaciente.setText("💾 Guardar");
    }

    // ════════════════════════════════════════════════════════
    // PESTAÑA 2: MEDICIONES
    // ════════════════════════════════════════════════════════
    private JPanel crearPestanaMediciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BLANCO);
        form.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AZUL_MEDIO, 1),
            "Nueva Medición", TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), AZUL_MEDIO));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboPaciente = new JComboBox<>();
        comboPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPresionS   = new JTextField(6);
        txtPresionD   = new JTextField(6);
        txtGlucosa    = new JTextField(6);
        txtFrecuencia = new JTextField(6);

        // Fila 0: Paciente
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=1;
        form.add(label("Paciente:"), gbc);
        gbc.gridx=1; gbc.gridwidth=5;
        form.add(comboPaciente, gbc);

        // Fila 1: Presión sistólica / diastólica
        agregarCampo(form, gbc, "Presión sistólica:", txtPresionS, 0, 1, 1);
        agregarCampo(form, gbc, "Presión diastólica:", txtPresionD, 2, 1, 1);

        // Fila 2: Glucosa / Frecuencia
        agregarCampo(form, gbc, "Glucosa (mg/dL):", txtGlucosa, 0, 2, 1);
        agregarCampo(form, gbc, "Frecuencia cardíaca:", txtFrecuencia, 2, 2, 1);

        // Resultado riesgo
        lblRiesgo = new JLabel("─────────────────────────────");
        lblRiesgo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRiesgo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=6;
        form.add(lblRiesgo, gbc);

        btnGuardarMedicion = boton("💾 Guardar Medición", VERDE);
        gbc.gridy=4;
        form.add(btnGuardarMedicion, gbc);

        btnGuardarMedicion.addActionListener(e -> guardarMedicion());

        panel.add(form, BorderLayout.NORTH);
        return panel;
    }

    private void guardarMedicion() {
        Paciente p = (Paciente) comboPaciente.getSelectedItem();
        if (p == null) { mostrarError("Seleccione un paciente."); return; }
        try {
            double ps  = Double.parseDouble(txtPresionS.getText().trim());
            double pd  = Double.parseDouble(txtPresionD.getText().trim());
            double glu = Double.parseDouble(txtGlucosa.getText().trim());
            int    fc  = Integer.parseInt(txtFrecuencia.getText().trim());

            ControlSalud c = new ControlSalud(p.getId(), ps, pd, glu, fc);
            String riesgo = EvaluadorRiesgo.evaluar(c);
            c.setRiesgo(riesgo);

            if (ControlSaludDAO.insertar(c)) {
                mostrarResultadoRiesgo(riesgo);
                txtPresionS.setText(""); txtPresionD.setText("");
                txtGlucosa.setText(""); txtFrecuencia.setText("");
            }
        } catch (NumberFormatException ex) {
            mostrarError("Todos los valores deben ser números válidos.");
        }
    }

    private void mostrarResultadoRiesgo(String riesgo) {
        Color color = switch (riesgo) {
            case "ALTO"  -> ROJO;
            case "MEDIO" -> AMARILLO;
            default      -> VERDE;
        };
        lblRiesgo.setForeground(color);
        lblRiesgo.setText("⚠ " + EvaluadorRiesgo.descripcion(riesgo));
        JOptionPane.showMessageDialog(this,
            EvaluadorRiesgo.descripcion(riesgo), "Resultado de la Medición",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ════════════════════════════════════════════════════════
    // PESTAÑA 3: HISTORIAL
    // ════════════════════════════════════════════════════════
    private JPanel crearPestanaHistorial() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        top.setBackground(GRIS_FONDO);
        comboHistorial = new JComboBox<>();
        comboHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboHistorial.setPreferredSize(new Dimension(250, 30));
        JButton btnVer = boton("🔍 Ver Historial", AZUL_MEDIO);
        top.add(label("Paciente:"));
        top.add(comboHistorial);
        top.add(btnVer);

        modeloHistorial = new DefaultTableModel(
            new String[]{"Fecha", "Presión S.", "Presión D.", "Glucosa", "Frec. Card.", "Riesgo"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaHistorial = crearTabla(modeloHistorial);
        tablaHistorial.setDefaultRenderer(Object.class, new RiesgoRenderer());

        btnVer.addActionListener(e -> cargarHistorial());

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        return panel;
    }

    private void cargarHistorial() {
        Paciente p = (Paciente) comboHistorial.getSelectedItem();
        if (p == null) return;
        modeloHistorial.setRowCount(0);
        List<ControlSalud> lista = ControlSaludDAO.obtenerPorPaciente(p.getId());
        for (ControlSalud c : lista) {
            modeloHistorial.addRow(new Object[]{
                c.getFechaMedicion(),
                c.getPresionSistolica(),
                c.getPresionDiastolica(),
                c.getGlucosa(),
                c.getFrecuenciaCardiaca(),
                c.getRiesgo()
            });
        }
        if (lista.isEmpty()) mostrarInfo("Este paciente no tiene mediciones registradas.");
    }

    // ════════════════════════════════════════════════════════
    // CARGA DE DATOS
    // ════════════════════════════════════════════════════════
    private void cargarPacientes() {
        modeloPacientes.setRowCount(0);
        comboPaciente.removeAllItems();
        comboHistorial.removeAllItems();

        List<Paciente> lista = PacienteDAO.obtenerTodos();
        for (Paciente p : lista) {
            modeloPacientes.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getEdad(),
                p.getPeso(), p.getEstatura(), p.getAntecedentes()
            });
            comboPaciente.addItem(p);
            comboHistorial.addItem(p);
        }
    }

    // ════════════════════════════════════════════════════════
    // HELPERS
    // ════════════════════════════════════════════════════════
    private void agregarCampo(JPanel p, GridBagConstraints g, String etiqueta,
                               JTextField campo, int col, int fila, int ancho) {
        g.gridx = col; g.gridy = fila; g.gridwidth = 1;
        p.add(label(etiqueta), g);
        g.gridx = col + 1; g.gridwidth = ancho;
        p.add(campo, g);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private JLabel label(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(AZUL_OSCURO);
        return l;
    }

    private JButton boton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setBackground(color);
        b.setForeground(BLANCO);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(7, 16, 7, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable t = new JTable(modelo);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setRowHeight(26);
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.getTableHeader().setBackground(AZUL_OSCURO);
        t.getTableHeader().setForeground(BLANCO);
        t.setSelectionBackground(AZUL_CLARO);
        t.setGridColor(new Color(220, 220, 225));
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return t;
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Renderer para colorear filas según el riesgo
    private class RiesgoRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String riesgo = (String) table.getValueAt(row, 5);
            if (!isSelected) {
                setBackground(switch (riesgo == null ? "" : riesgo) {
                    case "ALTO"  -> new Color(255, 220, 220);
                    case "MEDIO" -> new Color(255, 245, 200);
                    default      -> new Color(220, 245, 220);
                });
            }
            return this;
        }
    }
}
