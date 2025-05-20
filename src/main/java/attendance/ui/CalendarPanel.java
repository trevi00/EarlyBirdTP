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
    private ImageIcon checkIcon = null;

    public CalendarPanel(YearMonth yearMonth, Set<LocalDate> attendanceDays) {
        this.yearMonth = yearMonth;
        this.attendanceDays = attendanceDays;
        try {
            Image img = new ImageIcon(getClass().getResource("/img/check.png")).getImage();
            Image scaled = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            checkIcon = new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("❌ check.png 이미지 로드 실패: " + e.getMessage());
        }
        setLayout(new GridLayout(0, 7)); // 7열 (일~토)
        drawCalendar();
    }

    private void drawCalendar() {
        // 요일 헤더
        String[] weekDays = {"일", "월", "화", "수", "목", "금", "토"};
        for (int i = 0; i < 7; ++i) {
            String day = weekDays[i];
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            lbl.setOpaque(true);
            if(i == 0) {
                lbl.setForeground(Color.RED);
                lbl.setBackground(Color.PINK);
            } else if(i == 6) {
                lbl.setForeground(Color.BLUE);
                lbl.setBackground(Color.CYAN);
            }
            add(lbl);
        }

        LocalDate firstDay = yearMonth.atDay(1);
        int emptyStart = firstDay.getDayOfWeek().getValue() % 7;

        // 빈 칸
        for (int i = 0; i < emptyStart; i++) {
            JLabel label = new JLabel("");
            add(label);
            if(i == 0) {
                label.setBackground(Color.PINK);
                label.setOpaque(true);
            } else if(i == 6) {
                label.setBackground(Color.CYAN);
                label.setOpaque(true);
            }
        }

        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            boolean attended = attendanceDays.contains(date);

            JLabel label;

            if (attended) {
                // ✅ 출석일: 이미지 아이콘 표시
                label = new JLabel(checkIcon);
                label.setToolTipText("출석!");
            } else {
                // 일반 날짜 숫자
                label = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            }

            int currentDay = (7 + day - emptyStart) % 7;
//            System.out.println(day + ", " + currentDay);
            if(currentDay == 0) {
                label.setForeground(Color.RED);
                label.setBackground(Color.PINK);
                label.setOpaque(true);
            } else if(currentDay == 6) {
                label.setForeground(Color.BLUE);
                label.setBackground(Color.CYAN);
                label.setOpaque(true);
            }

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            add(label);
        }
    }
}
