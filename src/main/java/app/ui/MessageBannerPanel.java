package app.ui;

import javax.swing.*;
import java.awt.*;

public class MessageBannerPanel extends JPanel {

    private final JLabel label;
    private Timer typingTimer;
    private Timer clearTimer;

    public MessageBannerPanel() {
        setLayout(new BorderLayout());

        // 텍스트 라벨
        label = new JLabel(" ", SwingConstants.CENTER);
        label.setFont(new Font("나눔스퀘어라운드", Font.PLAIN, 15));
        label.setForeground(new Color(80, 50, 150));
        label.setOpaque(false); // 라벨 자체 배경은 투명
        label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(label, BorderLayout.CENTER);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(new Color(200, 180, 255), 2, true)
        ));

        setVisible(false);
    }

    // 🎨 배경 그라데이션 오버라이딩
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Graphics2D로 변환
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        // 💕 파스텔톤 그라데이션 설정 (왼쪽에서 오른쪽)
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 230, 250),      // 연핑크
                w, 0, new Color(230, 255, 255));     // 하늘색

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
    }

    public void showMessage(String message) {
        String fullMessage = "🐣 " + message;
        label.setText(" ");
        setVisible(true);

        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
        }
        if (clearTimer != null && clearTimer.isRunning()) {
            clearTimer.stop();
        }

        final StringBuilder currentText = new StringBuilder();
        final int[] index = {0};

        typingTimer = new Timer(40, e -> {
            if (index[0] < fullMessage.length()) {
                currentText.append(fullMessage.charAt(index[0]++));
                label.setText(currentText.toString());
            } else {
                typingTimer.stop();

                clearTimer = new Timer(3000, evt -> {
                    label.setText(" ");
                    setVisible(false);
                });
                clearTimer.setRepeats(false);
                clearTimer.start();
            }
        });

        typingTimer.start();
    }
}
