package repository.todo;

import Entity.Todo;
import database.DatabaseConnection;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class JdbcTodoRepository implements TodoRepository {

    final Connection connection = DatabaseConnection.getConnection();

    @Override
    public void save(Todo todo) {

    }

    @Override
    public Todo findByUsernameAndDate(String username, LocalDate date) {
        return null;
    }

    @Override
    public List<Todo> findAllByUsername(String username) {
        return List.of();
    }

    @Override
    public void delete(String username, LocalDate date) {

    }

    @Override
    public boolean exists(String username, LocalDate date) {
        return false;
    }

    @Override
    public void markAsDone(String username, LocalDate date) {

    }
}
