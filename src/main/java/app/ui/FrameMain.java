package app.ui;

import app.context.EarlyBirdContext;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * [FrameMain]
 * - 로그인한 사용자의 username을 기반으로 시스템 초기화
 */
public class

FrameMain extends JFrame {

    private EarlyBirdContext context;

    /**
     * @param username 로그인한 사용자 이름
     */
    public FrameMain(String username) {
        setGlobalUIStyle();

        setTitle("EarlyBird 🌅");
        setSize(500, 600);
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ 로그인한 사용자의 username으로 context 구성
        this.context = new EarlyBirdContext(username);

        // 메인 메뉴 패널 생성
        MainMenuPanel mainPanel = new MainMenuPanel(context);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        // 상단 고정 배너 포함한 전체 레이아웃 구성
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(context.getBannerPanel(), BorderLayout.NORTH);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        setContentPane(wrapper);
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

        UIManager.put("Button.background", new Color(135, 206, 250));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Label.foreground", Color.DARK_GRAY);
    }

    // 테스트용 main은 생략하거나 필요 시 다음과 같이 사용:
    // public static void main(String[] args) {
    //     new FrameMain("test1");
    // }
}
