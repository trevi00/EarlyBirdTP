package attendance.ui;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChickLabelFrame extends JFrame {
    public ChickLabelFrame() {
        // 1. 이미지 불러오기 (리소스에서)
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/출석/chick_bg.png")));

        // 2. 이미지 크기 얻기
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        // 3. 라벨 생성 및 이미지 적용
        JLabel label = new JLabel(icon);

        // 4. 라벨 크기 맞추기
        label.setSize(width, height);
        label.setPreferredSize(new Dimension(width, height));

        // 5. 프레임 크기도 이미지에 맞추기
        setTitle("출석 체크");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        pack(); // 라벨 크기에 맞게 프레임 자동 조정
        setLocationRelativeTo(null); // 화면 중앙 배치
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChickLabelFrame::new);
    }

}
