package todo.service;

import bird.point.PointService;
import todo.model.ToDo;
import todo.repository.ToDoRepository;

import java.time.LocalDate;
import java.util.List;

public class DefaultToDoService implements ToDoService {

    private final ToDoRepository repository;
    private final PointService pointService;

    public DefaultToDoService(ToDoRepository repository, PointService pointService) {
        this.repository = repository;
        this.pointService = pointService;
    }

    @Override
    public boolean add(ToDo todo) {
        return repository.save(todo);
    }

    @Override
    public boolean hasToDoToday(String username) {
        return repository.exists(username, LocalDate.now());
    }

    @Override
    public ToDo findTodayToDo(String username) {
        return repository.findByUsernameAndDate(username, LocalDate.now());
    }

    @Override
    public List<ToDo> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void markAsDone(String id) {
        ToDo todo = repository.findById(id);
        if (todo != null && !todo.isDone()) {
            repository.markAsDone(id);
            pointService.addPoint(todo.getUsername(), 2);  // ✅ 할 일 1개 완료 시 2점 증가
        }
    }

    @Override
    public ToDo findById(String id) {
        return repository.findById(id);
    }
}
