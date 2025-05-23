package bird.ui;

import bird.model.Bird;
import bird.point.PointService;
import bird.service.BirdService;
import bird.message.BirdMessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * [FrameBird]
 * - 새의 상태를 시각적으로 보여주는 화면 (그림 + 텍스트)
 * - 새를 클릭하면 메시지를 말함
 * - 진화하기 버튼으로 수동 진화 가능
 */
public class FrameBird extends JFrame {

    private final Bird bird;
    private final BirdService birdService;
    private final BirdMessageManager messageManager;
    private final BirdRenderer birdRenderer;
    private final JLabel lblBirdInfo;
    private final PointService pointService;
    private final JButton btnEvolve;

    public FrameBird(Bird bird, BirdService birdService, BirdMessageManager messageManager, PointService pointService) {
        this.bird = bird;
        this.birdService = birdService;
        this.messageManager = messageManager;
        this.pointService = pointService;

        setTitle("마이 얼리버드");
        setSize(300, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 배경 패널
        JPanel backgroundPanel = new JPanel() {
            Image bgImage = new ImageIcon(getClass().getResource("/img/마이얼리버드/Earlybird_Bird_DEM.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this); // 전체 배경
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel); // 배경 패널을 프레임 contentPane으로 설정

        // 새 그림 그리는 패널
        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false); // 배경 투명
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // 수평 중앙 정렬
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20,13,0,0));

        birdRenderer = new BirdRenderer(bird);
        birdRenderer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        birdRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageManager.speakRandom();
            }
        });

        imagePanel.add(birdRenderer); // 이미지 가운데 배치
        add(imagePanel, BorderLayout.CENTER);

        // 새 상태 텍스트
        lblBirdInfo = new JLabel("", SwingConstants.CENTER);
        lblBirdInfo.setOpaque(false);
        lblBirdInfo.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        lblBirdInfo.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        add(lblBirdInfo, BorderLayout.NORTH);

        // 진화 버튼
        
        // 하단 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // 수평 중앙 정렬
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); // 아래쪽 30px 여백
        buttonPanel.setOpaque(false); // 배경 투명 처리

        // 버튼 생성 및 크기 설정
        btnEvolve = new JButton("진화하기");
        btnEvolve.setPreferredSize(new Dimension(120, 30)); // 크기 설정
        btnEvolve.setOpaque(true);
        btnEvolve.setContentAreaFilled(true);
        // 색 지정
        btnEvolve.setBackground(new Color(190, 227, 248));
        btnEvolve.setForeground(Color.BLACK);
        btnEvolve.setBorderPainted(true);
        btnEvolve.setFocusPainted(false);
        // 초기 버튼 활성화 상태 설정
        btnEvolve.setEnabled(birdService.canEvolve(bird));
        
        btnEvolve.addActionListener(e -> {
            if (!birdService.canEvolve(bird)) {
                messageManager.say("진화할 수 없습니다. 포인트가 부족하거나 최종 단계입니다.");
            } else {
                birdService.evolve(bird);  // 딱 한 번만
                String msg = "진화 성공!\n현재 단계: " + bird.getStage().getName();
                JOptionPane.showMessageDialog(
                        FrameBird.this,
                        msg,
                        "진화 결과",
                        JOptionPane.INFORMATION_MESSAGE
                );
                refresh();
            }
            // 버튼 활성화 상태 갱신
            btnEvolve.setEnabled(birdService.canEvolve(bird));
        });

        buttonPanel.add(btnEvolve);
        add(buttonPanel, BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    /**
     * 새 상태를 새로 고친다 (진화 후 또는 초기 표시)
     */
    public void refresh() {
        String info = "<html><div style='text-align:center; width:220px;'>" +
                "🌟 포인트: " + pointService.getCurrentPoint(bird.getUsername()) + "점" +
                "</div></html>";
        lblBirdInfo.setText(info);
        birdRenderer.repaint();
    }
}
