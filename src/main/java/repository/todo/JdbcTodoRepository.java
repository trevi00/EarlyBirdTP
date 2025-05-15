package repository.todo;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcTodoRepository implements TodoRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
