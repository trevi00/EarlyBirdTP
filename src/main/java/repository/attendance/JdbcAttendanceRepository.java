package repository.attendance;

import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class JdbcAttendanceRepository implements AttendanceRepository {

    final Connection connection = DatabaseConnection.getConnection();

    @Override
    public boolean existsByDate(String user_id, LocalDate attend_date) throws SQLException {
        String sql = "select * from attendance where user_id = ? and attend_date = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setDate(2, Date.valueOf(attend_date));
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
    public void save(String user_id, LocalDate attend_date) throws SQLException {
        String sql = "insert into attendance(user_id, attend_date) values(?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setDate(2, Date.valueOf(attend_date));
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if(preparedStatement != null) preparedStatement.close();
        }
    }
}
