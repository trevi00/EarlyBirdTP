package Entity;

import java.time.LocalDate;

public class Attendance {
    /*
    user_id     varchar2(64) references users(id), -- 출석한 유저 아이디
    attend_date date -- 출석일자
     */

    private String user_id;

    private LocalDate attend_date;

    public Attendance(String user_id) {
        this.user_id = user_id;
        attend_date = LocalDate.now();
    }

    public Attendance(String user_id, LocalDate attend_date) {
        this.user_id = user_id;
        this.attend_date = attend_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(LocalDate attend_date) {
        this.attend_date = attend_date;
    }
}
