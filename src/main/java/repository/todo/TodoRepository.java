package repository.todo;

import java.time.LocalDate;

public interface TodoRepository {

    void save(Todo todo);

    Todo findByUsernameAndDate(String username, LocalDate date);

    List<Todo> findAllByUsername(String username);

    void delete(String username, LocalDate date);

    boolean exists(String username, LocalDate date);

    void markAsDone(String username, LocalDate date);

}
