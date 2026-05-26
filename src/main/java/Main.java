import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:D:/Practice/DB/mini-banking.db";

    public static void main(String[] args) {
        testConnection();
    }

    // Метод для тестирования подключения к БД
    public static void testConnection() {
        // Создаем объект подключения (изначально пустой)
        Connection connection = null;

        try {
            // Пытаемся установить соединение по указанному пути
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Ура! Подключение к базе данных SQLite прошло успешно.");

        } catch (SQLException e) {
            // Если путь указан неверно или базы нет, программа не вылетит,
            // а аккуратно выведет ошибку в консоль
            System.out.println("Ошибка подключения: " + e.getMessage());

        } finally {
            // Блок finally выполняется ВСЕГДА.
            // Это хорошее правило тона в программировании: открыл соединение с БД — закрой его.
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
