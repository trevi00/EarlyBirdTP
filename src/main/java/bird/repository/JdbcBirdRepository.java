package bird.repository;

import bird.model.Bird;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcBirdRepository implements BirdRepository {

    private final Connection conn;

    public JdbcBirdRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Bird findByUsername(String username) {
        String sql = "SELECT stage FROM BIRDS WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String stageName = rs.getString("stage");
                Bird bird = new Bird(username);
                bird.setStage(stageName); // setter 추가 필요
                return bird;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Bird(username); // 없으면 기본 생성
    }

    @Override
    public void save(Bird bird) {
        String sql = "MERGE INTO BIRDS b USING dual ON (b.username = ?) " +
                "WHEN MATCHED THEN UPDATE SET b.stage = ? " +
                "WHEN NOT MATCHED THEN INSERT (username, stage) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bird.getUsername());
            stmt.setString(2, bird.getStage().name());
            stmt.setString(3, bird.getUsername());
            stmt.setString(4, bird.getStage().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
