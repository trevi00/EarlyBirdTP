package team_prototype.repository.attendance;

import java.time.LocalDate;

public interface AttendanceRepository {

    boolean existsByDate(String user_id, LocalDate attend_date) throws Exception;

    void save(String user_id, LocalDate attend_date) throws Exception;

}
