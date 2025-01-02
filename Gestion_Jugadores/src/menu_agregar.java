import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class menu_agregar {
    private JTextField nombre;
    private JTextField posicion;
    private JTextField equipo;
    private JTextField edad;
    private JButton guardar;
    private JButton cancelarButton;
    public JPanel menu_agregar;

    public menu_agregar() {
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores ingresados en los campos de texto
                String nombreJugador = nombre.getText();
                String posicionJugador = posicion.getText();
                String equipoJugador = equipo.getText();
                int edadJugador = Integer.parseInt(edad.getText()); // Convertir edad de String a int

                // Guardar el nuevo jugador en la base de datos
                guardarJugador(nombreJugador, posicionJugador, equipoJugador, edadJugador);

                // Cerrar el formulario de agregar
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(menu_agregar);
                topFrame.dispose(); // Cerrar la ventana actual
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el formulario de agregar y volver al menú principal
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(menu_agregar);
                topFrame.dispose(); // Cerrar la ventana actual
            }
        });
    }

    // Método para guardar un nuevo jugador en la base de datos
    private void guardarJugador(String nombre, String posicion, String equipo, int edad) {
        // Conexión a la base de datos
        try (Connection conn = ConexionDB.getConnection()) {
            // Consulta SQL para insertar un nuevo jugador
            String query = "INSERT INTO Jugadores (nombre, posicion, equipo, edad) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Establecer los valores de los parámetros en la consulta
            stmt.setString(1, nombre);
            stmt.setString(2, posicion);
            stmt.setString(3, equipo);
            stmt.setInt(4, edad);

            // Ejecutar la consulta
            stmt.executeUpdate();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Jugador agregado con éxito!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar jugador.");
        }
    }
}
