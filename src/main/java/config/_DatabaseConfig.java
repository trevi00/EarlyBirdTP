package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * [DatabaseConfig]
 * - Oracle DB 연결을 위한 설정 클래스
 * - getConnection() 호출 시 DB에 연결된 Connection 객체 반환
 */
public class _DatabaseConfig {

    public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // ✅ PDB 이름
    public static final String USER = "earlybird";   // ✅ 새 계정
    public static final String PASSWORD = "12345";  // ✅ 새 비번

    /**
     * DB 연결 객체 반환
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("DB 연결 실패: " + e.getMessage());
            return null;
        }
    }
}