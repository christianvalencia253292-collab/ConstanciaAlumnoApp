package edu.itson.constancia.ui;

import edu.itson.constancia.controller.Controlador;
import edu.itson.constancia.model.Alumno;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

/** Vista principal (Swing). Tres columnas: resultados, detalle, constancia. */
public class MainFrame extends JFrame {

    // Entrada y resultados
    private JTextField txtId;
    private JList<Alumno> lstResultados;
    private DefaultListModel<Alumno> listModel;

    // Detalle/confirmación
    private JLabel lblNombre;
    private JLabel lblId;
    private JLabel lblSemestre;
    private JLabel lblMaterias;
    private JButton btnGenerar;

    // Constancia
    private JEditorPane paneConstancia;

    // Controlador
    private Controlador controlador;

    public MainFrame() {
        super("Generar Constancia - ITSON");
        initComponents();
    }

    public void setController(final Controlador c) {
        this.controlador = c;
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel izquierdo: entrada
        final JPanel left = new JPanel(new GridBagLayout());
        left.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        final GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.WEST;
        left.add(new JLabel("ID del alumno:"), gc);

        gc.gridy++;
        txtId = new JTextField(20);
        left.add(txtId, gc);

        gc.gridy++;
        final JLabel hint = new JLabel("Teclea el ID; a la derecha verás coincidencias.");
        hint.setForeground(Color.GRAY);
        left.add(hint, gc);

        // Filtrado en vivo
        txtId.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { onChange(); }
            public void removeUpdate(DocumentEvent e) { onChange(); }
            public void changedUpdate(DocumentEvent e) { onChange(); }
            private void onChange() {
                if (controlador != null) controlador.buscarPorPrefijo(txtId.getText());
            }
        });

        add(left, BorderLayout.WEST);

        // Panel derecho: 3 columnas
        final JPanel right = new JPanel(new GridLayout(1, 3, 12, 12));
        right.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Columna 1: Coincidencias
        final JPanel col1 = new JPanel(new BorderLayout(8, 8));
        col1.add(new JLabel("Coincidencias"), BorderLayout.NORTH);
        listModel = new DefaultListModel<Alumno>();
        lstResultados = new JList<Alumno>(listModel);
        lstResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstResultados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && controlador != null) {
                final Alumno a = lstResultados.getSelectedValue();
                if (a != null) controlador.cargarDetalle(a.getId());
            }
        });
        col1.add(new JScrollPane(lstResultados), BorderLayout.CENTER);

        // Columna 2: Confirmación
        final JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        col2.add(new JLabel("Confirmación"));
        col2.add(Box.createVerticalStrut(8));
        lblNombre   = new JLabel("Nombre: -");
        lblId       = new JLabel("ID: -");
        lblSemestre = new JLabel("Semestre: -");
        lblMaterias = new JLabel("Materias inscritas: -");
        btnGenerar  = new JButton("Generar Constancia");
        btnGenerar.addActionListener(e -> {
            if (controlador != null) controlador.generarConstancia();
        });
        col2.add(lblNombre);
        col2.add(lblId);
        col2.add(lblSemestre);
        col2.add(lblMaterias);
        col2.add(Box.createVerticalStrut(12));
        col2.add(btnGenerar);

        // Columna 3: Constancia
        final JPanel col3 = new JPanel(new BorderLayout());
        col3.add(new JLabel("Constancia"), BorderLayout.NORTH);
        paneConstancia = new JEditorPane();
        paneConstancia.setContentType("text/html");
        paneConstancia.setEditable(false);
        col3.add(new JScrollPane(paneConstancia), BorderLayout.CENTER);

        right.add(col1);
        right.add(col2);
        right.add(col3);

        add(right, BorderLayout.CENTER);
    }

    // Actualizaciones desde el controlador
    public void mostrarCoincidencias(final List<Alumno> alumnos) {
        listModel.clear();
        if (alumnos != null) {
            for (Alumno a : alumnos) listModel.addElement(a);
        }
    }

    public void mostrarConfirmacion(final Alumno a) {
        if (a == null) {
            lblNombre.setText("Nombre: -");
            lblId.setText("ID: -");
            lblSemestre.setText("Semestre: -");
            lblMaterias.setText("Materias inscritas: -");
        } else {
            lblNombre.setText("Nombre: " + a.getNombre());
            lblId.setText("ID: " + a.getId());
            lblSemestre.setText("Semestre: " + a.getSemestre());
            lblMaterias.setText("Materias inscritas: " + a.getMateriasInscritas());
        }
        paneConstancia.setText("");
    }

    public void mostrarConstancia(final String html) {
        paneConstancia.setText(html != null ? html : "");
        paneConstancia.setCaretPosition(0);
    }
}
