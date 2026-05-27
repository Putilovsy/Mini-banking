import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminWindow extends JFrame {

    // Конструктор панели администратора
    public AdminWindow() {
        setTitle("Мини-Банк: Панель Администратора");
        setSize(600, 400); // Окно делаем чуть побольше, чтобы влезла таблица
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("История всех транзакций", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "ID Отправителя", "ID Получателя", "Сумма", "Дата и время"};

        List<String[]> transactionsData = DatabaseRequest.getAllTransactions();

        String[][] data = new String[transactionsData.size()][5];
        for (int i = 0; i < transactionsData.size(); i++) {
            data[i] = transactionsData.get(i);
        }

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        table.setEnabled(false); // Только чтение

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Выйти");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });
        panel.add(logoutButton, BorderLayout.SOUTH);

        add(panel);
    }
}