package ui;

import model.*;
import service.GestorReserva;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map; // Importar Map
import java.util.LinkedHashMap; // Importar LinkedHashMap para mantener el orden

// Importar la clase TitledBorder para resolver los campos DEFAULT_JUSTIFICATION y DEFAULT_POSITION
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame {

    private JComboBox<String> comboApartamentos;
    private JTextField campoNombre;
    private JTextField campoNumeroLibreta;
    private JTextField campoMes;
    private JTextField campoPartido;
    private JTextArea areaResultado;
    private JButton botonAsignar;
    private JButton botonLiberar; // Nuevo botón para liberar

    // Usaremos una lista para gestionar las JCheckBox de servicios dinámicamente
    private List<JCheckBox> servicioCheckBoxes;
    // Mapa para almacenar los objetos ServiciosExtras y sus CheckBoxes asociadas
    private Map<String, ServiciosExtras> serviciosDisponibles;

    private GestorReserva gestorReserva;

    // Esta lista ahora representa TODOS los apartamentos, disponibles o no.
    private List<Apartamento> todosLosApartamentos; 

    public MainFrame() {
        setTitle("Gestión de Apartamentos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 750); // Ajustar tamaño para más información
        setLocationRelativeTo(null);
        
        inicializarDatos();
        gestorReserva = new GestorReserva();
        
        initUI();
        actualizarComboBoxApartamentos(); // Llama a este método al inicio para poblar el combo box
    }

    private void inicializarDatos() {
        todosLosApartamentos = new ArrayList<>();
        todosLosApartamentos.add(new ApartamentoSimple(101, 2, 1500.0, "Apto Simple 101"));
        todosLosApartamentos.add(new ApartamentoDoble(102, 4, 2500.0, "Apto Doble 102"));
        todosLosApartamentos.add(new ApartamentoSimple(103, 2, 1600.0, "Apto Simple 103"));
        todosLosApartamentos.add(new ApartamentoDoble(201, 3, 2200.0, "Apto Doble 201"));
        todosLosApartamentos.add(new ApartamentoProletario(203, 1, 800.0, "Apto Proletario 203"));
        todosLosApartamentos.add(new ApartamentoSimple(202, 2, 1700.0, "Apto Simple 202"));

        // Inicializar los servicios disponibles como objetos ServiciosExtras
        serviciosDisponibles = new LinkedHashMap<>();
        serviciosDisponibles.put("Calefacción", new ServiciosExtras("Calefacción", 40.0));
        serviciosDisponibles.put("Rayo de Sol", new ServiciosExtras("Horas de Luz Solar", 70.0)); // Nombre UI a nombre canónico
        serviciosDisponibles.put("Aire Fresco", new ServiciosExtras("Aire Fresco", 60.0));
        serviciosDisponibles.put("Internet", new ServiciosExtras("Internet", 50.0));
        serviciosDisponibles.put("Limpieza", new ServiciosExtras("Limpieza", 30.0));
        serviciosDisponibles.put("Lavandería", new ServiciosExtras("Lavandería", 20.0));

        // Inicializar la lista de CheckBoxes
        servicioCheckBoxes = new ArrayList<>();
    }

    private void initUI() {
        setLayout(new BorderLayout(15, 15));

        Font largeFont = new Font("SansSerif", Font.PLAIN, 16);
        Font titleFont = new Font("SansSerif", Font.BOLD, 18);

        JPanel panelSuperior = new JPanel(new GridLayout(4, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder(null, "Datos del compatriota",
                                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont));

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(largeFont);
        panelSuperior.add(labelNombre);
        campoNombre = new JTextField();
        campoNombre.setFont(largeFont);
        panelSuperior.add(campoNombre);

        JLabel labelNumeroLibreta = new JLabel("Número de Libreta:");
        labelNumeroLibreta.setFont(largeFont);
        panelSuperior.add(labelNumeroLibreta);
        campoNumeroLibreta = new JTextField();
        campoNumeroLibreta.setFont(largeFont);
        panelSuperior.add(campoNumeroLibreta);

        JLabel labelMes = new JLabel("Mes de Ingreso:");
        labelMes.setFont(largeFont);
        panelSuperior.add(labelMes);
        campoMes = new JTextField();
        campoMes.setFont(largeFont);
        panelSuperior.add(campoMes);

        JLabel labelPartido = new JLabel("Partido Político:");
        labelPartido.setFont(largeFont);
        panelSuperior.add(labelPartido);
        campoPartido = new JTextField();
        campoPartido.setFont(largeFont);
        panelSuperior.add(campoPartido);

        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 15, 15));

        JPanel panelApartamento = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelApartamento.setBorder(BorderFactory.createTitledBorder(null, "Selección de Apartamento",
                                    TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont));
        JLabel labelApartamento = new JLabel("Apartamento: (Disp/Ocup)");
        labelApartamento.setFont(largeFont);
        panelApartamento.add(labelApartamento);
        comboApartamentos = new JComboBox<>();
        comboApartamentos.setFont(largeFont);
        panelApartamento.add(comboApartamentos);
        panelCentral.add(panelApartamento);

        JPanel panelServicios = new JPanel(new GridLayout(3, 2, 10, 10));
        panelServicios.setBorder(BorderFactory.createTitledBorder(null, "Servicios extra",
                                  TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont));

        // Generación dinámica de JCheckBoxes de servicios usando los objetos ServiciosExtras
        for (Map.Entry<String, ServiciosExtras> entry : serviciosDisponibles.entrySet()) {
            String uiName = entry.getKey(); // Nombre para la UI
            ServiciosExtras servicio = entry.getValue(); // Objeto ServiciosExtras

            JCheckBox cb = new JCheckBox(uiName + " ($" + String.format("%.0f", servicio.getPrecio()) + ")");
            cb.setFont(largeFont);
            servicioCheckBoxes.add(cb); // Añadir a la lista
            panelServicios.add(cb);
        }

        panelCentral.add(panelServicios);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        areaResultado = new JTextArea(12, 40);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 15));
        areaResultado.setBorder(BorderFactory.createTitledBorder(null, "Resultado de la Reserva",
                                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, titleFont));
        panelInferior.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        botonAsignar = new JButton("Asignar Apartamento y Reservar");
        botonAsignar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botonAsignar.addActionListener(this::asignarApartamento);
        panelBotonesAccion.add(botonAsignar);

        botonLiberar = new JButton("Liberar Apartamento");
        botonLiberar.setFont(new Font("SansSerif", Font.BOLD, 18));
        botonLiberar.addActionListener(this::liberarApartamento);
        panelBotonesAccion.add(botonLiberar);

        panelInferior.add(panelBotonesAccion, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el JComboBox de apartamentos para reflejar su disponibilidad.
     */
    private void actualizarComboBoxApartamentos() {
        comboApartamentos.removeAllItems(); // Limpiar ítems existentes
        for (Apartamento apto : todosLosApartamentos) { // Iterar sobre todos los apartamentos
            String estado = apto.isDisponible() ? "Disponible" : "Ocupado";
            comboApartamentos.addItem(apto.getTipo() + " " + apto.getNumero() + " (" + estado + ") | Base: $" + String.format("%.2f", apto.getPrecioBase()));
        }
    }

    private void asignarApartamento(ActionEvent e) {
        String nombre = campoNombre.getText().trim();
        String numeroLibreta = campoNumeroLibreta.getText().trim();
        String mesString = campoMes.getText().trim();
        String partido = campoPartido.getText().trim();
        
        int selectedApartmentIndex = comboApartamentos.getSelectedIndex();
        if (selectedApartmentIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un apartamento.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Apartamento apartamentoSeleccionado = todosLosApartamentos.get(selectedApartmentIndex);

        if (nombre.isEmpty() || numeroLibreta.isEmpty() || mesString.isEmpty() || partido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validación para el campoMes
        int mes;
        try {
            mes = Integer.parseInt(mesString);
            if (mes < 1 || mes > 12) {
                JOptionPane.showMessageDialog(this, "Error: El mes de ingreso debe ser un número entre 1 y 12.", "Mes Inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: El mes de ingreso debe ser un número válido.", "Formato de Mes Inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!apartamentoSeleccionado.isDisponible()) {
            JOptionPane.showMessageDialog(this, "Error: El apartamento seleccionado ya está ocupado.", "Apartamento Ocupado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Inquilino inquilino = new Inquilino(nombre, numeroLibreta, apartamentoSeleccionado, partido);

        // Recopilar objetos ServiciosExtras seleccionados
        List<ServiciosExtras> serviciosSeleccionados = new ArrayList<>();
        for (JCheckBox cb : servicioCheckBoxes) {
            if (cb.isSelected()) {
                // Extraer el nombre de la UI (ej. "Calefacción")
                String uiName = cb.getText().substring(0, cb.getText().lastIndexOf(" ($"));
                ServiciosExtras servicio = serviciosDisponibles.get(uiName);
                if (servicio != null) {
                    serviciosSeleccionados.add(servicio);
                }
            }
        }

        try {
            // Pasar la lista de objetos ServiciosExtras
            Reserva nuevaReserva = gestorReserva.crearReserva(apartamentoSeleccionado, inquilino, mesString, serviciosSeleccionados);
            if (nuevaReserva != null) {
                areaResultado.setText(nuevaReserva.obtenerDetallesReserva());
                actualizarComboBoxApartamentos(); // Actualizar el combo box después de una reserva exitosa
                JOptionPane.showMessageDialog(this, "Reserva realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar campos y desmarcar servicios
                campoNombre.setText("");
                campoNumeroLibreta.setText("");
                campoMes.setText("");
                campoPartido.setText("");
                for (JCheckBox cb : servicioCheckBoxes) {
                    cb.setSelected(false);
                }
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error en la reserva: " + ex.getMessage(), "Error de Reserva", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Listener para el botón "Liberar Apartamento".
     */
    private void liberarApartamento(ActionEvent e) {
        int selectedApartmentIndex = comboApartamentos.getSelectedIndex();
        if (selectedApartmentIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un apartamento para liberar.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Apartamento apartamentoSeleccionado = todosLosApartamentos.get(selectedApartmentIndex);

        if (gestorReserva.liberarApartamento(apartamentoSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Apartamento " + apartamentoSeleccionado.getNumero() + " ha sido liberado con éxito.", "Apartamento Liberado", JOptionPane.INFORMATION_MESSAGE);
            actualizarComboBoxApartamentos(); // Actualizar el combo box para reflejar el cambio de estado
        } else {
            JOptionPane.showMessageDialog(this, "El apartamento " + apartamentoSeleccionado.getNumero() + " ya estaba disponible o no se pudo liberar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}