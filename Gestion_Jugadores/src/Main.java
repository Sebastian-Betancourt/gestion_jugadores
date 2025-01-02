import javax.swing.*;  // Importa clases para crear interfaces gráficas (botones, cuadros de texto, etc.)
import java.sql.Connection;  // Permite manejar conexiones con bases de datos
import java.sql.DriverManager;  // Permite gestionar los controladores de base de datos
import java.sql.SQLException;  // Maneja excepciones relacionadas con operaciones de bases de datos
import java.awt.*;  // Proporciona clases para crear interfaces gráficas y gráficos (ventanas, imágenes, etc.)
//el usurio para entrar es: Sebas_b0
//contraseña 123456

// Clase para manejar la conexión a la base de datos
class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_jugadores";  // URL de conexión a la base de datos MySQL, especificando el nombre de la base de datos 'gestion_jugadores'
    private static final String USER = "root";  // Usuario para acceder a la base de datos
    private static final String PASSWORD = "123456";  // Contraseña del usuario para la base de datos

    public static Connection getConnection() throws SQLException {
        // Establece y devuelve una conexión a la base de datos utilizando los datos proporcionados (URL, usuario, contraseña)
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


// Clase principal que prueba la conexión a la base de datos
public class Main {
    public static void main(String[] args) {
        // Crear una nueva ventana JFrame llamada "Login"
        JFrame frame = new JFrame("Login");

        // Establecer el panel principal de la clase login como el contenido de la ventana
        frame.setContentPane(new login().mainpanel);

        // Establecer el comportamiento de cierre de la ventana: salir al cerrar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Establecer el tamaño de la ventana a 1024x768 píxeles
        frame.setSize(1024, 768);

        // Establecer el tamaño preferido de la ventana, útil para redimensionar automáticamente
        frame.setPreferredSize(new Dimension(1024, 768));

        // Ajustar el tamaño del frame según el tamaño preferido
        frame.pack();

        // Hacer la ventana visible
        frame.setVisible(true);
    }
}


