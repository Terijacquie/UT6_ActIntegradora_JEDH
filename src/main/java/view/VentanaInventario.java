package view;

import javax.swing.*;
import java.awt.*;

public class VentanaInventario extends JFrame {
    public JTextField txtNombre = new JTextField();
    public JTextField txtStock = new JTextField();
    public JTextField txtPrecio = new JTextField();
    public JComboBox<model.Secciones> comboSeccion = new JComboBox<>();
    public JButton btnActualizar = new JButton("Actualizar");
    public JButton btnBuscar = new JButton("Buscar");
    public JButton btnEliminar = new JButton("Eliminar");
    public JButton btnGuardar = new JButton("Guardar en Inventario");
    public JButton btnLimpiar = new JButton("Limpiar");

    // Tablita para ver los productos (read)
    public JTable tablaInventario = new JTable();
    public JScrollPane scrollTabla  = new JScrollPane(tablaInventario);


    public VentanaInventario() {
        setTitle("La Mafia - Gestión de Inventario");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel Central (formulario)
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2,10,10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        //Etiquetas y campos
        panelFormulario.add(new JLabel(" Producto:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel(" Stock:"));
        panelFormulario.add(txtStock);
        panelFormulario.add(new JLabel(" Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel(" Sección:"));
        panelFormulario.add(comboSeccion);

        // Panel norte de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscar);

        // Unimos lo paneles
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla,BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

}
