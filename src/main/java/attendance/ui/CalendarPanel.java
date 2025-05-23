package attendance.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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
    private BufferedImage background = null;

    public CalendarPanel(YearMonth yearMonth, Set<LocalDate> attendanceDays) {
        this.yearMonth = yearMonth;
        this.attendanceDays = attendanceDays;
        try {
            Image img = new ImageIcon(getClass().getResource("/img/출석현황/check.png")).getImage();
            Image scaled = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            checkIcon = new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + e.getMessage());
        }
        setLayout(new GridLayout(0, 7)); // 7열 (일~토)

        drawCalendar();
    }

    private void drawCalendar() {
        Font font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()[485];
//        for(int i = 0; i < allFonts.length; ++i) {
//            System.out.println(allFonts[i] + ", " + i);
//        }

        // 요일 헤더
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; ++i) {
            String day = weekDays[i];
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(font.deriveFont(16f).deriveFont(Font.BOLD));
            lbl.setForeground(new Color(150, 97, 71));
            add(lbl);
        }

        LocalDate firstDay = yearMonth.atDay(1);
        int emptyStart = firstDay.getDayOfWeek().getValue() % 7;

        // 빈 칸
        for (int i = 0; i < emptyStart; i++) {
            JLabel label = new JLabel("");
            add(label);
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
                label.setFont(label.getFont().deriveFont(15f).deriveFont(Font.BOLD));
                label.setForeground(new Color(150, 97, 71));
            }

            int currentDay = (7 + day - emptyStart) % 7;
//            System.out.println(day + ", " + currentDay);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            add(label);
        }
    }
}
