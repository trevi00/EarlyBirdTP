package attendance.repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcAttendanceStatsRepository implements AttendanceStatsRepository {

    private final Connection conn;

    public JdbcAttendanceStatsRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int count(String username) {
        String sql = "SELECT COUNT(*) FROM ATTENDANCE WHERE USERNAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public LocalDate findLast(String username) {
        String sql = "SELECT MAX(ATTEND_DATE) FROM ATTENDANCE WHERE USERNAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate(1);
                return date != null ? date.toLocalDate() : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LocalDate> findAll(String username) {
        String sql = "SELECT ATTEND_DATE FROM ATTENDANCE WHERE USERNAME = ? ORDER BY ATTEND_DATE DESC";
        List<LocalDate> result = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getDate(1).toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<LocalDate> findByYearMonth(String username, String yearMonth) {
        String sql = """
            SELECT ATTEND_DATE FROM ATTENDANCE
            WHERE USERNAME = ? AND TO_CHAR(ATTEND_DATE, 'YYYY-MM') = ?
        """;
        List<LocalDate> result = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, yearMonth);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getDate(1).toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
