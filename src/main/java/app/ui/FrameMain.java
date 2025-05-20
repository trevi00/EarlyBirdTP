package app.ui;

import app.context.EarlyBirdContext;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.net.URL;

<<<<<<< HEAD
/**
 * [FrameMain]
 * - 로그인한 사용자의 username을 기반으로 시스템 초기화
 */
public class

FrameMain extends JFrame {
=======
public class FrameMain extends JFrame {
>>>>>>> origin/gyu

    private EarlyBirdContext context;
    private Image bgImage;

    public FrameMain(String username) {
        setGlobalUIStyle();

        // === 배경 이미지 로드 ===
        try {
            URL imgUrl = getClass().getResource("/earlybird8.png");
            System.out.println("이미지 경로: " + imgUrl);  // ← 디버깅용

            if (imgUrl == null) {
                throw new IOException("earlybird.png 파일을 찾을 수 없습니다!");
            }
            bgImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            // 기본 배경색 설정 (옵션)
            bgImage = null;
            setBackground(new Color(240, 248, 255)); // 하늘색
        }

        setTitle("EarlyBird 🌅");
        setSize(500, 600);
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.context = new EarlyBirdContext(username);

        // 메인 메뉴 패널 생성
        MainMenuPanel mainPanel = new MainMenuPanel(context);
        mainPanel.setOpaque(false);  // 투명화

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);  // 스크롤 패널 투명화
        scrollPane.getViewport().setOpaque(false);  // 뷰포트 투명화

        // 상단 고정 배너 포함한 전체 레이아웃 구성
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false); // 배경 투명하게
        wrapper.add(context.getBannerPanel(), BorderLayout.NORTH);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        // === 배경 패널 생성 ===
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            {
                setOpaque(false); // ⭐ 투명 설정
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.add(wrapper, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private void setGlobalUIStyle() {
        Font font = new Font("맑은 고딕", Font.PLAIN, 14);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }

        UIManager.put("ScrollBar.thumb", new Color(135, 206, 250, 180));
        UIManager.put("ScrollBar.thumbHighlight", new Color(176, 224, 230));
        UIManager.put("ScrollBar.thumbDarkShadow", new Color(100, 149, 237));
        UIManager.put("ScrollBar.track", new Color(245, 245, 245));

    }

    // 테스트용 main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrameMain("test1"));
    }
}
