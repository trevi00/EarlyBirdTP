package attendance.ui;

import attendance.service.AttendanceStatsService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/출석현황/bg_brown.png"));
            bg = new JLabel(icon);
        } catch(Exception e) {
            e.printStackTrace();
        }

        setTitle("출석현황");
        setSize(400, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(bg);
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
        Font font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[485];
//        for(int i = 0; i < allFonts.length; ++i) {
//            System.out.println(allFonts[i] + ", " + i);
//        }
        monthLabel.setFont(font.deriveFont(20f).deriveFont(Font.BOLD));
        monthLabel.setForeground(new Color(150, 97, 71));
        monthPanel.add(monthLabel, SwingConstants.CENTER);
        monthPanel.setOpaque(false);
        add(monthPanel, BorderLayout.NORTH);

        List<LocalDate> dateList = statsService.getMonthlyAttendance(username, formatted);
        Set<LocalDate> attendanceSet = new HashSet<>(dateList);

        // ✅ CalendarPanel에 출석 날짜 전달
        CalendarPanel calendarPanel = new CalendarPanel(yearMonth, attendanceSet);
        calendarPanel.setOpaque(false);
        add(calendarPanel, BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.setOpaque(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}
