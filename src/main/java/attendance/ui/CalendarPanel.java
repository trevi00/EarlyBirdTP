package attendance.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

/**
 * [CalendarPanel]
 * - 특정 연월의 달력을 그리고, 출석일은 이미지로 강조 표시한다.
 */
public class CalendarPanel extends JPanel {

    private final YearMonth yearMonth;
    private final Set<LocalDate> attendanceDays;

    public CalendarPanel(YearMonth yearMonth, Set<LocalDate> attendanceDays) {
        this.yearMonth = yearMonth;
        this.attendanceDays = attendanceDays;
        setLayout(new GridLayout(0, 7)); // 7열 (일~토)
        drawCalendar();
    }

    private void drawCalendar() {
        // 요일 헤더
        String[] weekDays = {"일", "월", "화", "수", "목", "금", "토"};
        for (String day : weekDays) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            add(lbl);
        }

        LocalDate firstDay = yearMonth.atDay(1);
        int emptyStart = firstDay.getDayOfWeek().getValue() % 7;

        // 빈 칸
        for (int i = 0; i < emptyStart; i++) {
            add(new JLabel(""));
        }

        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            boolean attended = attendanceDays.contains(date);

            JLabel label;

            if (attended) {
                // ✅ 출석일: 이미지 아이콘 표시
                ImageIcon icon = loadCheckIcon();
                label = new JLabel(icon);
                label.setToolTipText("출석함");
            } else {
                // 일반 날짜 숫자
                label = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            }

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            add(label);
        }
    }

    private ImageIcon loadCheckIcon() {
        try {
            Image img = new ImageIcon(getClass().getResource("/img/check.png")).getImage();
            Image scaled = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("❌ check.png 이미지 로드 실패: " + e.getMessage());
            return new ImageIcon(); // fallback
        }
    }
}
