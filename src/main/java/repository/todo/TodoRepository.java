package repository.todo;

import Entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository {

    void save(Todo todo);

    Todo findByUsernameAndDate(String username, LocalDate date);

    List<Todo> findAllByUsername(String username);

    void delete(String username, LocalDate date);

    boolean exists(String username, LocalDate date);

    void markAsDone(String username, LocalDate date);

}
