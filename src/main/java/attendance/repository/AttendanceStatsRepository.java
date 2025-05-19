package attendance.repository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceStatsRepository {
    int count(String username);
    LocalDate findLast(String username);
    List<LocalDate> findAll(String username);
    List<LocalDate> findByYearMonth(String username, String yearMonth);
}
