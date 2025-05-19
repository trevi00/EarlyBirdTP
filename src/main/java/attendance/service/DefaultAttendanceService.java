package attendance.service;

import attendance.repository.AttendanceRepository;
import bird.point.PointService;

import java.time.LocalDate;
import java.time.LocalTime;

public class DefaultAttendanceService implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final PointService pointService;

    public DefaultAttendanceService(AttendanceRepository attendanceRepository, PointService pointService) {
        this.attendanceRepository = attendanceRepository;
        this.pointService = pointService;
    }

    @Override
    public boolean checkAttendance(String username, LocalDate today) {
        if (attendanceRepository.existsByDate(username, today)) {
            return false;
        }

        attendanceRepository.save(username, today);

        // ✅ 시간에 따라 포인트 차등 지급
        LocalTime now = LocalTime.now();
        int point;

        if (now.isAfter(LocalTime.of(4, 0)) && now.isBefore(LocalTime.of(10, 0))) {
            point = 6; // 04:00~10:00
        } else {
            point = 2; // 그 외 시간
        }

        pointService.addPoint(username, point);

        return true;
    }
}
