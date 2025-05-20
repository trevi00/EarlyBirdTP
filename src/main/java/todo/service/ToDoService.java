package todo.service;

import todo.model.ToDo;

import java.util.List;

public interface ToDoService {

    boolean add(ToDo todo);

    boolean hasToDoToday(String username);

    List<ToDo> findTodayToDo(String username);

    List<ToDo> findByUsername(String username);

    void markAsDone(String id);  // ✅ ID 기반 완료 처리

    void markAsUndone(String id);

    ToDo findById(String id);    // ✅ 완료 처리용 조회
}
