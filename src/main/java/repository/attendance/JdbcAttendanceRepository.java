package repository.attendance;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcAttendanceRepository implements AttendanceRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
