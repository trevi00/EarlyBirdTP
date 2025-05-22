package app.ui;

import javax.swing.*;
import java.awt.*;

public class MessageBannerPanel extends JPanel {

    private final JLabel label;

    public MessageBannerPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 248, 225)); // 크림 파스텔 배경

        // 💬 라벨 구성
        label = new JLabel(" ", SwingConstants.CENTER);
        label.setFont(new Font("나눔스퀘어라운드", Font.PLAIN, 24)); // 귀여운 폰트 추천: 나눔스퀘어라운드
        label.setForeground(new Color(80, 50, 150));
        label.setOpaque(false);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(label, BorderLayout.CENTER);

        // 🖼 둥근 테두리 효과
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 180, 255), 3, true)
        ));

        // 🌈 그림자 효과는 UIManager 또는 커스텀 페인팅으로 확장 가능
        setVisible(false); // 처음에는 안 보이도록
    }

    public void showMessage(String message) {
        label.setText(message); // 아이콘 추가
        setVisible(true);

        // ✨ 페이드 아웃 효과 (옵션)
        Timer timer = new Timer(4000, e -> {
            label.setText(" ");
            setVisible(false);
        });
        timer.setRepeats(false);
        timer.start();
    }
}
