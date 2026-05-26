import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {

    public ClientWindow(String username) {
        // Базовые настройки окна
        setTitle("Мини-Банк: Личный кабинет [" + username + "]");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Добро пожаловать, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Обращаемся к БД за балансом
        Double balance = DatabaseRequest.getClientBalance(username);

        String balanceText;
        if (balance != null) {
            balanceText = "Ваш баланс: " + balance + " руб.";
        } else {
            balanceText = "Счет не найден. Обратитесь в банк.";
        }

        JLabel balanceLabel = new JLabel(balanceText, SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(balanceLabel, BorderLayout.CENTER);

        // Добавляем панель в окно
        add(panel);
    }
}