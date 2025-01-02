import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    private JTextField textField1; // Campo de texto para el nombre de usuario
    private JPasswordField passwordField1; // Campo de texto para la contraseña (JPasswordField para mayor seguridad)
    private JButton Verificar; // Botón para verificar login
    private JLabel Usuario; // Etiqueta para "Usuario"
    private JLabel Contraseña; // Etiqueta para "Contraseña"
    public JPanel mainpanel; // Panel principal de la interfaz

    public login() {
        // Acción al presionar el botón Verificar
        Verificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText(); // Obtener el texto del campo de usuario
                String contrasena = new String(passwordField1.getPassword()); // Obtener la contraseña como String

                // Verificar si las credenciales son correctas
                if (verificarCredenciales(usuario, contrasena)) {
                    // Crear y mostrar la ventana del menú
                    JFrame frame = new JFrame("Menu");
                    frame.setContentPane(new menu().menu);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(1024, 768);
                    frame.setPreferredSize(new Dimension(1024, 768));
                    frame.pack();
                    frame.setVisible(true);

                    // Cerrar la ventana de login
                    ((JFrame) SwingUtilities.getWindowAncestor(mainpanel)).dispose();
                } else {
                    // Si las credenciales son incorrectas, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(mainpanel, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Método para verificar las credenciales en la base de datos
    private boolean verificarCredenciales(String usuario, String contrasena) {
        boolean esValido = false;
        try (Connection conn = ConexionDB.getConnection()) {
            // SQL para verificar si el usuario y la contraseña coinciden en la base de datos
            String query = "SELECT * FROM Usuarios WHERE usuario = ? AND contrasena = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                esValido = true; // Si se encuentran registros, las credenciales son correctas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esValido;
    }
}
