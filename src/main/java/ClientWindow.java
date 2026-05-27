import javax.swing.*;
import java.awt.*;

public class ClientWindow extends JFrame {

    // Конструктор главного окна клиента
    public ClientWindow(String username) {
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

        JButton transferButton = new JButton("Перевести деньги");
        panel.add(transferButton, BorderLayout.SOUTH);

        transferButton.addActionListener(e -> {
            String targetAccount = JOptionPane.showInputDialog(this, "Введите номер счета получателя:");
            if (targetAccount == null || targetAccount.trim().isEmpty()) return;

            String amountStr = JOptionPane.showInputDialog(this, "Введите сумму перевода:");
            if (amountStr == null || amountStr.trim().isEmpty()) return;

            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Сумма должна быть больше нуля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = DatabaseRequest.transferMoney(username, targetAccount, amount);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Перевод успешно выполнен!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new ClientWindow(username).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка перевода. Проверьте баланс и номер счета.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Неверный формат суммы!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
    }
}