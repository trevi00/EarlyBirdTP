package user.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    public RoundedButton(String text, Color bgColor) {
        super(text);
        setContentAreaFilled(false);                 // 내부 배경 직접 그림
        setFocusPainted(false);                      // 포커스 테두리 제거
        setForeground(Color.WHITE);                  // 텍스트 색상
        setBackground(bgColor);                      // 배경 색상
        setFont(new Font("맑은 고딕", Font.BOLD, 15)); // 글꼴 스타일
        setBorder(null);                             // 테두리 제거
        // setOpaque(false);                         // 필요 시 직접 조정
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 🔲 둥근 배경 버튼 채우기
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();

        super.paintComponent(g); // 🔤 텍스트 출력
    }

    @Override
    protected void paintBorder(Graphics g) {
        // ❌ 테두리 없음
    }
}
