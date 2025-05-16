package repository.attendance;

import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class JdbcAttendanceRepository implements AttendanceRepository {

    final Connection connection = DatabaseConnection.getConnection();

    @Override
    public boolean existsByDate(String username, LocalDate date) throws SQLException {
        final String sql = "select id from attendance where user_id = ? and attend_date = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setDate(2, Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if(preparedStatement != null) preparedStatement.close();
            if(resultSet != null) resultSet.close();
        }
    }

    @Override
    public void save(String username, LocalDate date) throws SQLException {
    }
}
