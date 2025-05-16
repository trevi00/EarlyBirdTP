package repository.attendance;

import java.time.LocalDate;

public interface AttendanceRepository {

    boolean existsByDate(String username, LocalDate data) throws Exception;

    void save(String username, LocalDate date) throws Exception;

}
