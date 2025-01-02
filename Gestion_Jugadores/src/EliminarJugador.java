import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarJugador {
    public JPanel EliminarJugador;
    private JLabel nombree;
    private JTextField nombre;
    private JButton Buscar;
    private JButton Eliminar;
    private JPanel resultados;
    private JButton Cancelar;

    public EliminarJugador() {
        // Asegurarse de que el panel 'resultados' tenga un layout adecuado
        resultados.setLayout(new BorderLayout()); // Usamos un BorderLayout para agregar JScrollPane o JLabels

        Eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreJugador = nombre.getText().trim(); // Obtener el nombre del campo de texto

                if (nombreJugador.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarJugador, "Por favor ingresa el nombre del jugador.");
                    return;
                }

                // Llamar al método para eliminar el jugador de la base de datos
                eliminarJugadorPorNombre(nombreJugador);
            }
        });

        Buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre ingresado en el JTextField
                String nombreBusqueda = nombre.getText().toLowerCase(); // Convertir a minúsculas

                // Limpiar los resultados previos
                resultados.removeAll();
                resultados.revalidate();
                resultados.repaint();

                // Buscar jugadores en la base de datos
                buscarJugadorPorNombre(nombreBusqueda);
            }
        });
        Cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(EliminarJugador);
                topFrame.dispose(); // Cerrar la ventana actual
            }
        });
    }

    // Método para eliminar jugador de la base de datos por nombre
    private void eliminarJugadorPorNombre(String nombreJugador) {
        // Conectar a la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "DELETE FROM Jugadores WHERE LOWER(nombre) = ?"; // Consulta SQL para eliminar el jugador

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreJugador.toLowerCase()); // Pasar el nombre a minúsculas para no tener en cuenta mayúsculas/minúsculas

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(EliminarJugador, "Jugador eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(EliminarJugador, "No se encontró un jugador con ese nombre.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(EliminarJugador, "Error al eliminar jugador: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para buscar un jugador por nombre (sin importar mayúsculas/minúsculas)
    private void buscarJugadorPorNombre(String nombreBusqueda) {
        // Crear la conexión a la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            String query = "SELECT * FROM Jugadores WHERE LOWER(nombre) LIKE ?"; // SQL para no importar mayúsculas/minúsculas
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + nombreBusqueda + "%"); // El '%' es para buscar por coincidencia parcial

            ResultSet rs = stmt.executeQuery();

            // Crear una tabla para mostrar los resultados
            String[] columnas = {"ID", "Nombre", "Posición", "Equipo", "Edad"};
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
            JTable tabla = new JTable(modelo);

            // Agregar los datos a la tabla
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("posicion");
                fila[3] = rs.getString("equipo");
                fila[4] = rs.getInt("edad");

                modelo.addRow(fila);
            }

            // Si no se encontraron resultados
            if (modelo.getRowCount() == 0) {
                JLabel mensaje = new JLabel("No se encontraron jugadores.");
                resultados.add(mensaje, BorderLayout.CENTER); // Agregar el JLabel con el mensaje de no encontrado en el panel 'resultados'
            } else {
                JScrollPane scrollPane = new JScrollPane(tabla);
                resultados.add(scrollPane, BorderLayout.CENTER); // Agregar la tabla con los resultados al panel 'resultados'
            }

            // Actualizar el panel de resultados para mostrar los cambios
            resultados.revalidate();
            resultados.repaint();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(EliminarJugador, "Error al buscar jugador: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
