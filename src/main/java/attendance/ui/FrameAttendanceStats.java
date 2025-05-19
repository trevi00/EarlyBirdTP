package attendance.ui;

import attendance.service.AttendanceStatsService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrameAttendanceStats extends JFrame {

    private final AttendanceStatsService statsService;
    private final String username;

    public FrameAttendanceStats(AttendanceStatsService statsService, String username) {
        this.statsService = statsService;
        this.username = username;

        setTitle("출석 통계 📊");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel summaryPanel = new JPanel(new GridLayout(2, 1));
        int totalDays = statsService.getTotalAttendanceCount(username);
        LocalDate lastDate = statsService.getLastAttendanceDate(username);

        JLabel totalLabel = new JLabel("총 출석일 수: " + totalDays);
        JLabel lastLabel = new JLabel("마지막 출석일: " + (lastDate != null ? lastDate.toString() : "없음"));

        summaryPanel.add(totalLabel);
        summaryPanel.add(lastLabel);

        add(summaryPanel, BorderLayout.NORTH);

        // ✅ 날짜 포맷을 "yyyy-MM" 형식으로 맞추어 전달
        YearMonth yearMonth = YearMonth.now();
        String formatted = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<LocalDate> dateList = statsService.getMonthlyAttendance(username, formatted);
        Set<LocalDate> attendanceSet = new HashSet<>(dateList);

        // ✅ CalendarPanel에 출석 날짜 전달
        CalendarPanel calendarPanel = new CalendarPanel(yearMonth, attendanceSet);
        add(calendarPanel, BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
