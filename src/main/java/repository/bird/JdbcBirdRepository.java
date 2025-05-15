package repository.bird;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcBirdRepository implements BirdRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
