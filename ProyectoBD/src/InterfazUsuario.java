import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// La clase InterfazUsuario hereda de JFrame para crear una interfaz gráfica
public class InterfazUsuario extends JFrame {
    private JTextField txtProducto; // Campo de texto para ingresar el nombre del producto
    private JTextField txtCosto; // Campo de texto para ingresar el costo del producto
    private JTextField txtStock; // Campo de texto para ingresar el stock del producto
    
    private Conexion conexion; // Instancia de la clase Conexion para interactuar con la base de datos

    // Constructor de la clase InterfazUsuario
    public InterfazUsuario() {
        super("Editar Productos"); // Título de la ventana
        conexion = new Conexion(); // Inicializa la conexión a la base de datos

        initComponents(); // Inicializar componentes de la interfaz

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina la aplicación al cerrar la ventana
        setSize(609, 331); // Tamaño inicial de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true); // Hace visible la ventana
    }

    // Método para inicializar los componentes de la interfaz
    private void initComponents() {
        JPanel panel = new JPanel(); // Crea un panel para organizar los componentes
        panel.setBackground(Color.WHITE); // Establece el color de fondo del panel

        // Etiquetas y campos de texto para ingresar información sobre el producto
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setBounds(121, 11, 75, 32);
        lblProducto.setForeground(Color.black);
        txtProducto = new JTextField(10);
        txtProducto.setBackground(new Color(255, 255, 255));
        txtProducto.setBounds(197, 11, 212, 32);

        JLabel lblCosto = new JLabel("Costo:");
        lblCosto.setBounds(121, 62, 66, 32);
        lblCosto.setForeground(Color.black);
        txtCosto = new JTextField(10);
        txtCosto.setBounds(197, 62, 212, 32);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(121, 113, 66, 32);
        lblStock.setForeground(Color.black);
        txtStock = new JTextField(10);
        txtStock.setBounds(197, 113, 212, 32);

        // Botones para buscar, modificar y eliminar productos
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(396, 206, 187, 32);
        btnBuscar.setBackground(new Color(255, 128, 0));
        btnBuscar.setForeground(new Color(0, 0, 0));
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        JButton btnModificar = new JButton("Agregar/Modificar");
        btnModificar.setBounds(396, 163, 187, 32);
        btnModificar.setBackground(Color.gray);
        btnModificar.setForeground(Color.black);
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(396, 249, 187, 32);
        btnEliminar.setBackground(new Color(255, 0, 0));
        btnEliminar.setForeground(Color.black);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        
        panel.setLayout(null);

        // Agregar componentes al panel
        panel.add(lblProducto);
        panel.add(txtProducto);
        panel.add(lblCosto);
        panel.add(txtCosto);
        panel.add(lblStock);
        panel.add(txtStock);
        panel.add(btnBuscar);
        panel.add(btnModificar);
        panel.add(btnEliminar);

        // Agregar panel al frame
        getContentPane().add(panel);
        
        // Etiqueta para mostrar una imagen
        JLabel lblImagen = new JLabel("");
        lblImagen.setToolTipText("");
        lblImagen.setLabelFor(this);
        lblImagen.setRequestFocusEnabled(false);
        lblImagen.setHorizontalTextPosition(SwingConstants.LEFT);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        // Establece la imagen desde una ruta específica
        lblImagen.setIcon(new ImageIcon("C:\\xampp\\htdocs\\proyecto\\imagenes\\mastambo.jpg"));
        lblImagen.setBounds(0, -25, 593, 308);
        panel.add(lblImagen);
    }

    // Método para buscar un producto en la base de datos
    private void buscarProducto() {
        String nombreProducto = txtProducto.getText();
        boolean existe = conexion.existeProducto(nombreProducto);
        if (existe) {
            double costo = conexion.obtenerCostoProducto(nombreProducto);
            int stock = conexion.obtenerStockProducto(nombreProducto);
            txtCosto.setText(String.valueOf(costo));
            txtStock.setText(String.valueOf(stock));
            JOptionPane.showMessageDialog(this, "Producto encontrado en la base de datos.");
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado en la base de datos.");
        }
    }

    // Método para modificar un producto en la base de datos
    private void modificarProducto() {
        String nombreProducto = txtProducto.getText();
        String nuevoCostoStr = txtCosto.getText();
        String nuevoStockStr = txtStock.getText();
        
        if (nombreProducto.isEmpty() || nuevoCostoStr.isEmpty() || nuevoStockStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese todos los campos.");
            return;
        }
        
        try {
            double nuevoCosto = Double.parseDouble(nuevoCostoStr);
            int nuevoStock = Integer.parseInt(nuevoStockStr);
            
            if (conexion.existeProducto(nombreProducto)) {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Desea modificar el costo y el stock del producto?",
                        "Modificar Producto", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    conexion.modificarProducto(nombreProducto, nuevoCosto, nuevoStock);
                    JOptionPane.showMessageDialog(this, "Costo y stock del producto modificados correctamente.");
                }
            } else {
                conexion.agregarProducto(nombreProducto, nuevoCosto, nuevoStock);
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para el costo y el stock.");
        }
    }

    // Método para eliminar un producto de la base de datos
    private void eliminarProducto() {
        String nombreProducto = txtProducto.getText();
        conexion.eliminarProducto(nombreProducto);
        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {             
               new InterfazUsuario();
            }
        });
    }
}
