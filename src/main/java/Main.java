import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:D:/Practice/DB/mini-banking";

    public static void main(String[] args) {
        testConnection();

        SwingUtilities.invokeLater(() -> {
            LoginWindow window = new LoginWindow();
            window.setVisible(true);
        });
    }

    public static void testConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Ура! Подключение к базе данных SQLite прошло успешно.");

        } catch (SQLException e) {
            System.out.println("Ошибка подключения: " + e.getMessage());

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при закрытии соединения: " + ex.getMessage());
            }
        }
    }
}
