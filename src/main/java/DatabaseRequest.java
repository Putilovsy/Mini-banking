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

    public static boolean transferMoney(String senderUsername, String targetAccountNumber, double amount) {
        String checkBalanceSql = "SELECT a.id, a.balance FROM accounts a JOIN users u ON a.user_id = u.id WHERE u.username = ?";
        String checkTargetSql = "SELECT id FROM accounts WHERE account_number = ?";
        String updateSenderSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        String updateReceiverSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        String insertTransactionSql = "INSERT INTO transactions (sender_account_id, receiver_account_id, amount) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkSenderStmt = conn.prepareStatement(checkBalanceSql);
                 PreparedStatement checkTargetStmt = conn.prepareStatement(checkTargetSql);
                 PreparedStatement updateSenderStmt = conn.prepareStatement(updateSenderSql);
                 PreparedStatement updateReceiverStmt = conn.prepareStatement(updateReceiverSql);
                 PreparedStatement insertTxStmt = conn.prepareStatement(insertTransactionSql)) {

                checkSenderStmt.setString(1, senderUsername);
                ResultSet senderRs = checkSenderStmt.executeQuery();
                if (!senderRs.next() || senderRs.getDouble("balance") < amount) {
                    conn.rollback();
                    return false;
                }
                int senderAccountId = senderRs.getInt("id");

                checkTargetStmt.setString(1, targetAccountNumber);
                ResultSet targetRs = checkTargetStmt.executeQuery();
                if (!targetRs.next()) {
                    conn.rollback();
                    return false;
                }
                int targetAccountId = targetRs.getInt("id");

                updateSenderStmt.setDouble(1, amount);
                updateSenderStmt.setInt(2, senderAccountId);
                updateSenderStmt.executeUpdate();

                updateReceiverStmt.setDouble(1, amount);
                updateReceiverStmt.setInt(2, targetAccountId);
                updateReceiverStmt.executeUpdate();

                insertTxStmt.setInt(1, senderAccountId);
                insertTxStmt.setInt(2, targetAccountId);
                insertTxStmt.setDouble(3, amount);
                insertTxStmt.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Ошибка во время транзакции: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД: " + e.getMessage());
            return false;
        }
    }

    public static java.util.List<String[]> getAllTransactions() {
        java.util.List<String[]> transactionsList = new java.util.ArrayList<>();

        String sql = "SELECT id, sender_account_id, receiver_account_id, amount, timestamp FROM transactions";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String senderId = String.valueOf(rs.getInt("sender_account_id"));
                String receiverId = String.valueOf(rs.getInt("receiver_account_id"));
                String amount = String.valueOf(rs.getDouble("amount"));
                String timestamp = rs.getString("timestamp");

                transactionsList.add(new String[]{id, senderId, receiverId, amount, timestamp});
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении транзакций: " + e.getMessage());
        }

        return transactionsList;
    }
}