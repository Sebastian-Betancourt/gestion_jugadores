import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class menu {
    public JPanel menu;
    private JButton Ver;
    private JButton Agregar;
    private JButton Buscar;
    private JButton Eliminar;
    private JButton Salir;

    public menu() {
        Ver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método para cargar los jugadores en la tabla
                cargarJugadores();
            }
        });
        Agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método para mostrar el formulario de agregar jugador
                mostrarFormularioAgregar();
            }
        });
        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarjugador();
            }
        });
        Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarjugador();
            }
        });
        Salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
    }

    // Método para cargar los jugadores desde la base de datos y mostrarlos en una tabla
    private void cargarJugadores() {
        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();

        // Establecer las columnas (Asegúrate de que coincidan con las columnas de la tabla Jugadores)
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Posicion");
        modelo.addColumn("Equipo");
        modelo.addColumn("Edad");

        // Crear la tabla con el modelo
        JTable tablaJugadores = new JTable(modelo);

        // Establecer la tabla dentro de un JScrollPane para que sea desplazable
        JScrollPane scrollPane = new JScrollPane(tablaJugadores);
        scrollPane.setPreferredSize(new Dimension(800, 400)); // Ajusta el tamaño del JScrollPane

        // Crear un panel para agregar la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout()); // Usar BorderLayout para agregar el JScrollPane
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        // Obtener los jugadores desde la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT * FROM Jugadores"; // Consulta para obtener todos los jugadores de la tabla Jugadores
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Obtener los datos de cada jugador (ajusta los índices según las columnas de tu tabla)
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id"); // Columna 'id'
                fila[1] = rs.getString("nombre"); // Columna 'nombre'
                fila[2] = rs.getString("posicion"); // Columna 'posicion'
                fila[3] = rs.getString("equipo"); // Columna 'equipo'
                fila[4] = rs.getInt("edad"); // Columna 'edad'

                // Agregar la fila a la tabla
                modelo.addRow(fila);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Crear un nuevo JFrame para mostrar la tabla
        JFrame frameTabla = new JFrame("Jugadores");
        frameTabla.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo la ventana
        frameTabla.setSize(1024, 768); // Ajusta el tamaño de la ventana
        frameTabla.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frameTabla.add(panelTabla); // Agregar el panel con la tabla al frame
        frameTabla.setVisible(true); // Hacer visible la ventana
    }
    private void mostrarFormularioAgregar() {
        JFrame frame = new JFrame("Agregar");
        frame.setContentPane(new menu_agregar().menu_agregar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024,768);
        frame.setPreferredSize(new Dimension(1024,768));
        frame.pack();
        frame.setVisible(true);
    }
    private void buscarjugador (){
        JFrame frame = new JFrame("Buscar");
        frame.setContentPane(new BuscarJugador().BuscarJugador);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024,768);
        frame.setPreferredSize(new Dimension(1024,768));
        frame.pack();
        frame.setVisible(true);
    }
    private void eliminarjugador (){
        JFrame frame = new JFrame("Eliminar");
        frame.setContentPane(new EliminarJugador().EliminarJugador);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024,768);
        frame.setPreferredSize(new Dimension(1024,768));
        frame.pack();
        frame.setVisible(true);
    }
}
