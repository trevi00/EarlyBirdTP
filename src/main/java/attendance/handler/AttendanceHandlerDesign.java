package attendance.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class AttendanceHandlerDesign {

    /**
     * 메시지 팝업을 띄웁니다.
     * @param parent   부모 프레임
     * @param messageType  1: 얼리버드, 2: 일반출석
     */
    public static void showAttendanceMessage(JFrame parent, int messageType) {
        // 1. 배경 이미지 불러오기
        String imagePath;
        if (messageType == 3) {
            imagePath = "img/X.png"; // 실패 이미지
        } else {
            imagePath = "img/O.png"; // 성공 이미지
        }

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(
                AttendanceHandlerDesign.class.getClassLoader().getResource(imagePath)
        ));
        Image bgImage = bgIcon.getImage();

        int width = bgIcon.getIconWidth();
        int height = bgIcon.getIconHeight();

        // 2. 메시지/색상
        String message;
        Color textColor;
        if (messageType == 1) {
            message = "<html><div style='text-align:center;'>🎉 출석 완료!<br>얼리버드인 당신<br>대단해요!</div></html>";
            textColor = new Color(60, 140, 210); // 하늘색 계열
        } else if (messageType == 2) {
            message = "<html><div style='text-align:center;'>출석 완료!<br>내일은 더 일찍 만나요!</div></html>";
            textColor = new Color(0, 32, 96); // 남색 계열
        }else{
            message = "<html><div style='text-align:center;'>이미 오늘 출석을<br>완료했습니다!</div></html>"; // ⭐️ 이미 출석 메시지
            textColor = new Color(200, 120, 40);
        }

        // 3. 커스텀 다이얼로그 생성
        JDialog dialog = new JDialog(parent, "출석 안내", true);
        dialog.setUndecorated(true); // 테두리 없음

        // 4. 배경 패널
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));

        // 5. 메시지 라벨
        JLabel msgLabel = new JLabel(message, SwingConstants.CENTER);
        msgLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        msgLabel.setForeground(textColor);
        msgLabel.setBounds(125, 35, 200, 60);

        panel.add(msgLabel);

        // 6. 확인 버튼
        JButton btnOk = new JButton("확인");
        btnOk.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        btnOk.setBackground(new Color(190, 227, 248));
        btnOk.setForeground(Color.DARK_GRAY);
        btnOk.setFocusPainted(false);
        btnOk.setBorderPainted(false);
        btnOk.addActionListener(e -> dialog.dispose());
        panel.add(btnOk);

        if (messageType == 3) {
            btnOk.setBounds(175, 92, 100, 30); // 실패(X.png)용 위치
        } else {
            btnOk.setBounds(175, 100, 100, 30); // 성공(O.png)용 위치
        }
        panel.add(btnOk);

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}