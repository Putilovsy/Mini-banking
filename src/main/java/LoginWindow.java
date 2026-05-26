import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow() {
        setTitle("Мини-Банк: Авторизация");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 2, 10, 10));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Создание и добавление элементов на панель
        panel.add(new JLabel("Логин:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Пароль:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel(""));
        loginButton = new JButton("Войти");
        panel.add(loginButton);

        add(panel);

        // Обработка нажатия на кнопку
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                String role = DatabaseRequest.authenticateUser(username, password);

                if (role != null) {
                    if (role.equals("CLIENT")) {
                        dispose();

                        ClientWindow clientWindow = new ClientWindow(username);
                        clientWindow.setVisible(true);

                    } else if (role.equals("ADMIN")) {
                        // later...
                        JOptionPane.showMessageDialog(LoginWindow.this,
                                "Панель администратора пока в разработке!",
                                "Инфо", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginWindow.this,
                            "Неверный логин или пароль!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}