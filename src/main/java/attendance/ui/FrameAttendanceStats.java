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

        setTitle("출석기록");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setVisible(true);

        // ✅ 날짜 포맷을 "yyyy-MM" 형식으로 맞추어 전달
        YearMonth yearMonth = YearMonth.now();
        String formatted = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        JPanel monthPanel = new JPanel();
        String monthText = "<html><div style='text-align:center;'>" +
                yearMonth.format(DateTimeFormatter.ofPattern("M")) + " 월"
                + "</div></html>";
        JLabel monthLabel = new JLabel(monthText);
        monthLabel.setFont(monthLabel.getFont().deriveFont(Font.BOLD));
        monthPanel.add(monthLabel, SwingConstants.CENTER);
        add(monthPanel, BorderLayout.NORTH);

        List<LocalDate> dateList = statsService.getMonthlyAttendance(username, formatted);
        Set<LocalDate> attendanceSet = new HashSet<>(dateList);

        // ✅ CalendarPanel에 출석 날짜 전달
        CalendarPanel calendarPanel = new CalendarPanel(yearMonth, attendanceSet);
        add(calendarPanel, BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.setOpaque(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
