import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoginWindow window = new LoginWindow();
            window.setVisible(true);
        });
    }
}
