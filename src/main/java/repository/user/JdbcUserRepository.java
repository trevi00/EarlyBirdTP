package repository.user;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcUserRepository implements UserRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
