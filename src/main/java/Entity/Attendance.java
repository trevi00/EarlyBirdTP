package Entity;

import java.time.LocalDate;

public class Attendance {
    /*
    user_id     varchar2(64) references users(id), -- 출석한 유저 아이디
    attend_date date -- 출석일자
     */

    private String user_id;

    private LocalDate date;

    public Attendance(String user_id) {
        this.user_id = user_id;
        date = LocalDate.now();
    }

    public Attendance(String user_id, LocalDate date) {
        this.user_id = user_id;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
