package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() { // 싱글턴
        if(connection != null) {
            return connection;
        }
        try {
            connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB 연결 시도 중 오류 발생");
            System.exit(0);
        }
        return connection;
    }
}
