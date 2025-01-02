import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Importa la clase que permite usar tablas con un modelo predeterminado.
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BuscarJugador {
    // El panel principal de la clase BuscarJugador
    public JPanel BuscarJugador;
    private JButton Buscar; // Botón para iniciar la búsqueda
    private JLabel nombretitulo; // Título de la búsqueda
    private JTextField nombre; // Campo de texto donde el usuario ingresa el nombre del jugador
    private JPanel resultados; // Panel donde se mostrarán los resultados de la búsqueda
    private JButton Cancelar; // Botón para cancelar la operación y cerrar la ventana

    // Constructor donde se configuran los ActionListeners para los botones
    public BuscarJugador() {
        // Acción del botón Buscar
        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre ingresado en el campo de texto y convertirlo a minúsculas
                String nombreBusqueda = nombre.getText().toLowerCase(); // Convertir a minúsculas

                // Limpiar los resultados previos
                resultados.removeAll();
                resultados.revalidate(); // Revalidar el panel
                resultados.repaint(); // Volver a pintar el panel para limpiar la vista

                // Llamar al método que realiza la búsqueda en la base de datos
                buscarJugadorPorNombre(nombreBusqueda);
            }
        });

        // Acción del botón Cancelar
        Cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana actual (formulario de búsqueda) y volver al menú principal
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(BuscarJugador);
                topFrame.dispose(); // Cerrar la ventana actual
            }
        });
    }

    // Método para buscar jugadores por nombre en la base de datos
    private void buscarJugadorPorNombre(String nombreBusqueda) {
        // Crear la conexión a la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            // Consulta SQL para buscar jugadores cuyo nombre coincida (sin importar mayúsculas/minúsculas)
            String query = "SELECT * FROM Jugadores WHERE LOWER(nombre) LIKE ?"; // SQL para no importar mayúsculas/minúsculas
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + nombreBusqueda + "%"); // El '%' es para realizar la búsqueda por coincidencia parcial

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery();

            // Definir las columnas de la tabla que mostrará los resultados
            String[] columnas = {"ID", "Nombre", "Posición", "Equipo", "Edad"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0); // Crear el modelo de la tabla sin filas inicialmente
            JTable tabla = new JTable(modelo); // Crear la tabla con el modelo definido

            // Agregar los datos obtenidos de la base de datos a la tabla
            while (rs.next()) {
                Object[] fila = new Object[5]; // Crear una fila para cada jugador
                fila[0] = rs.getInt("id"); // Colocar el ID
                fila[1] = rs.getString("nombre"); // Colocar el nombre
                fila[2] = rs.getString("posicion"); // Colocar la posición
                fila[3] = rs.getString("equipo"); // Colocar el equipo
                fila[4] = rs.getInt("edad"); // Colocar la edad

                modelo.addRow(fila); // Añadir la fila al modelo de la tabla
            }

            // Si no se encontraron resultados en la base de datos
            if (modelo.getRowCount() == 0) {
                // Mostrar un mensaje indicando que no se encontraron jugadores
                JLabel mensaje = new JLabel("No se encontraron jugadores.");
                resultados.add(mensaje); // Añadir el mensaje al panel de resultados
            } else {
                // Si se encontraron jugadores, crear un JScrollPane para poder desplazarse por los resultados
                JScrollPane scrollPane = new JScrollPane(tabla);
                // Configurar el layout para agregar el JScrollPane al panel de resultados
                resultados.setLayout(new BorderLayout());
                resultados.add(scrollPane, BorderLayout.CENTER); // Añadir el JScrollPane al centro del panel
            }

            // Actualizar el panel de resultados para que se muestren los cambios
            resultados.revalidate(); // Revalidar el panel para aplicar los cambios
            resultados.repaint(); // Redibujar el panel
        } catch (SQLException ex) {
            // Manejar posibles errores en la consulta SQL
            ex.printStackTrace();
        }
    }
}
