package attendance.service;

import attendance.repository.AttendanceRepository;
import bird.point.PointManager;
import bird.point.PointService;
import user.session.SessionManager;

import java.time.LocalDate;

/**
 * [DefaultAttendanceService]
 * - 출석 여부 확인 및 저장 로직 구현
 */
public class DefaultAttendanceService implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final PointManager pointManager;
    private final PointService pointService;;

    public DefaultAttendanceService(AttendanceRepository attendanceRepository, PointManager pointManager, PointService pointService) {
        this.attendanceRepository = attendanceRepository;
        this.pointManager = pointManager;
        this.pointService = pointService;

    }

    @Override
    public boolean checkAttendance(String username, LocalDate today) {
        if (attendanceRepository.existsByDate(username, today)) {
            return false;
        }

        attendanceRepository.save(username, today);
        pointService.addPoint(username, 10); // 출석 시 포인트 적립
        return true;
    }
}
