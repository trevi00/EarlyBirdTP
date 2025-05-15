package repository.point;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcPointRepository implements PointRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
