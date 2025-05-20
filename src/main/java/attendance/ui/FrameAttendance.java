package attendance.ui;

import attendance.handler.AttendanceHandler;
import attendance.service.AttendanceService;
import bird.message.BirdMessageManager;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.PointManager;
import bird.point.PointService;
import bird.service.BirdService;
import bird.ui.FrameBird;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Objects;

public class FrameAttendance extends JFrame {

    static class ImagePanel extends JPanel {
        private final Image image;
        private final int width, height;

        public ImagePanel(Image image, int width, int height) {
            this.image = image;
            this.width = width;
            this.height = height;
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, width, height, this);
        }
    }

    public FrameAttendance(AttendanceService attendanceService,
                           PointManager pointManager,
                           Bird bird,
                           BirdService birdService,
                           BirdMessageProvider birdMessageProvider,
                           BirdMessageManager messageManager,
                           PointService pointService) {

        setTitle("출석 체크");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 1. 크기 지정
        int width = 350;
        int height = 350;

        // 2: 이미지 리소스 불러오기 및 리사이즈
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/chick_bg.png")));

        // 3. 배경 이미지 라벨 생성 및 레이아웃 설정
        ImagePanel backgroundPanel = new ImagePanel(originalIcon.getImage(), width, height);
        backgroundPanel.setLayout(null);

        // 4. 텍스트 라벨
        JLabel label = new JLabel("<html>오늘도 출석하고<br>포인트를 쌓아보세요!</html>", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        label.setForeground(new Color(44, 62, 80));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        label.setBounds(50, 80, 250, 60);

        // 5. 출석 버튼
        JButton btnCheck = new JButton("출석하기");
        btnCheck.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        btnCheck.setBounds(60, 170, 120, 40);
        btnCheck.setBackground(new Color(116, 204, 116));
        btnCheck.setForeground(Color.WHITE);             // 글씨 하얗게
        btnCheck.setFocusPainted(false);                 // 포커스 테두리 제거
        btnCheck.setBorderPainted(false);                // 테두리 선 제거
        btnCheck.setOpaque(true);                        // 배경색 보장

        // 6. 배경 라벨 위 텍스트, 버튼 올리기
        backgroundPanel.add(label);
        backgroundPanel.add(btnCheck);

        // 7. 프레임에 배경 라벨 추가
        setContentPane(backgroundPanel);
        pack(); // ⭐ 변경: pack()으로 패널 크기에 맞춤
        setLocationRelativeTo(null); // 화면 중앙 배치
        setResizable(false);

        // ✅ 인자 개수 맞춤
        // FrameBird frameBird = new FrameBird(bird, birdService, messageManager, pointService);

        btnCheck.addActionListener(e -> {
            AttendanceHandler handler = new AttendanceHandler(
                    attendanceService,
                    bird,
                    birdService,
                    birdMessageProvider,
                    // frameBird,
                    messageManager,
                    pointService
            );

            boolean result = handler.handleAttendance(this, bird.getUsername(), LocalDate.now());
            if (result) {
                dispose();
            }
        });

        setVisible(true);
    }
}
