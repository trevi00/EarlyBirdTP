package Entity;

import java.time.LocalDate;

public class Todo {
    /*
    id         varchar2(64) primary key, -- 투두 아이디
    user_id    varchar2(64) references users(id), -- 투두 리스트를 작성한 유저 아이디
    todo_date  date, -- 작성된 일자
    content    varchar2(200), -- 해야될 일
    done       char(1) check (done in ('y', 'n')) -- 수행 여부 체크
     */

    private int id;

    private String user_id;

    private LocalDate todo_date;

    private String content;

    private char done;

    public Todo(int id, String user_id, String content) {
        this.id = id;
        this.user_id = user_id;
        this.content = content;
        todo_date = LocalDate.now();
        done = 'n';
    }

    public Todo(int id, char done, String content, LocalDate todo_date, String user_id) {
        this.id = id;
        this.done = done;
        this.content = content;
        this.todo_date = todo_date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(LocalDate todo_date) {
        this.todo_date = todo_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public char getDone() {
        return done;
    }

    public void setDone(char done) {
        this.done = done;
    }
}
