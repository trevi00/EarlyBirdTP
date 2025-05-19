package bird.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPointRepository implements PointRepository {

    private final Connection conn;

    public JdbcPointRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int findPointByUsername(String username) {
        String sql = "SELECT total_point FROM POINTS WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_point");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 기본값
    }

    @Override
    public void updatePoint(String username, int newPoint) {
        String sql = "MERGE INTO POINTS p USING dual ON (p.username = ?) " +
                "WHEN MATCHED THEN UPDATE SET p.total_point = ? " +
                "WHEN NOT MATCHED THEN INSERT (username, total_point) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, newPoint);
            pstmt.setString(3, username);
            pstmt.setInt(4, newPoint);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
