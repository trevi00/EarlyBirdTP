package todo.repository;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.List;

/**
 * [ToDoRepository]
 * - 할 일 데이터 저장소 인터페이스
 */
public interface ToDoRepository {

    boolean save(ToDo todo);

    boolean exists(String username, LocalDate date);

    void markAsDone(String id); // ✅ 개별 할 일 기준으로 변경

    ToDo findByUsernameAndDate(String username, LocalDate date);

    List<ToDo> findByUsername(String username);

    ToDo findById(String id);
}
