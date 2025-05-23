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
    private JLabel bg = null;

    public FrameAttendanceStats(AttendanceStatsService statsService, String username) {
        this.statsService = statsService;
        this.username = username;

        setTitle("출석현황");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 255, 255));
        getContentPane().setVisible(true);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setVisible(true);

        // ✅ 날짜 포맷을 "yyyy-MM" 형식으로 맞추어 전달
        YearMonth yearMonth = YearMonth.now();
        String formatted = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        char[] monthChars = yearMonth.getMonth().toString().toCharArray();
        for(int i = 1; i < monthChars.length; ++i) {
            monthChars[i] = Character.toLowerCase(monthChars[i]);
        }
        JPanel monthPanel = new JPanel();
        String monthText = "<html><div style='text-align:center;'>" +
                String.valueOf(monthChars) + "</div></html>";
        JLabel monthLabel = new JLabel(monthText);
        monthLabel.setFont(monthLabel.getFont().deriveFont(Font.BOLD));
        monthPanel.add(monthLabel, SwingConstants.CENTER);
        monthPanel.setBackground(new Color(255, 255, 255));
        add(monthPanel, BorderLayout.NORTH);

        List<LocalDate> dateList = statsService.getMonthlyAttendance(username, formatted);
        Set<LocalDate> attendanceSet = new HashSet<>(dateList);

        // ✅ CalendarPanel에 출석 날짜 전달
        CalendarPanel calendarPanel = new CalendarPanel(yearMonth, attendanceSet);
        calendarPanel.setBackground(new Color(255, 255, 255));
        add(calendarPanel, BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.setOpaque(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
