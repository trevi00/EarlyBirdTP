package user.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int arc;

    public RoundedPanel(int arc) {
        this.arc = arc;
        setOpaque(false); // 🔲 배경은 우리가 직접 그릴 거니까 투명 처리
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 🎨 둥근 배경 그리기
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();

        // 🔽 자식 컴포넌트(버튼, 텍스트필드 등) 보이게 하기 위해 마지막 호출
        super.paintComponent(g);
    }
}
