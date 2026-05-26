import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseRequest {

    private static final String DB_URL = "jdbc:sqlite:D:/Practice/DB/mini-banking";

    public static String authenticateUser(String username, String password) {
        String role = null;

        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при авторизации: " + e.getMessage());
        }

        return role;
    }

    // Метод для получения баланса клиента по его логину
    public static Double getClientBalance(String username) {
        Double balance = null;

        String sql = "SELECT a.balance FROM accounts a " +
                "JOIN users u ON a.user_id = u.id " +
                "WHERE u.username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении баланса: " + e.getMessage());
        }

        return balance;
    }
}