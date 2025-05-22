package app.ui;

import javax.swing.*;
import java.awt.*;

public class MessageBannerPanel extends JPanel {

    private final JLabel label;

    public MessageBannerPanel() {
        setLayout(new BorderLayout());
        label = new JLabel(" ", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 34)); // 귀여운 폰트로 대체
        label.setOpaque(true);
        label.setBackground(new Color(255, 255, 204));// img 배경으로 대체
        label.setForeground(Color.DARK_GRAY);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 15, 0, Color.GRAY));
        add(label, BorderLayout.CENTER);
    }

    public void showMessage(String message) {
        label.setText(message);
        setVisible(true);

        Timer timer = new Timer(4000, e -> label.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }

}
