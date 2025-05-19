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

    public FrameBird(Bird bird, BirdService birdService, BirdMessageManager messageManager, PointService pointService) {
        this.bird = bird;
        this.birdService = birdService;
        this.messageManager = messageManager;
        this.pointService = pointService;

        setTitle("새 보기");
        setSize(300, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 새 그림 그리는 패널
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // 수평 중앙 정렬
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0,13,0,0));
        
        birdRenderer = new BirdRenderer(bird);
        birdRenderer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        birdRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageManager.speakRandom();
            }
        });
        
        imagePanel.add(birdRenderer); // 이미지 가운데 배치
        add(imagePanel, BorderLayout.CENTER); // CENTER 위치에 imagePanel 삽입

        // 새 상태 텍스트
        lblBirdInfo = new JLabel("", SwingConstants.CENTER);
        lblBirdInfo.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        lblBirdInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        add(lblBirdInfo, BorderLayout.NORTH);

        // 하단 버튼
        JPanel buttonPanel = new JPanel();
        JButton btnEvolve = new JButton("🌱 진화하기");

        btnEvolve.addActionListener(e -> {
            if (birdService.canEvolve(bird)) {
                birdService.evolve(bird); // 내부에서 포인트 소모 + 단계 변경
                messageManager.say("🎉 진화 성공! 현재 단계: " + bird.getStage().getName());
                refresh();
            } else {
                messageManager.say("⚠ 진화할 수 없습니다. 포인트가 부족하거나 최종 단계입니다.");
            }
        });
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));

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
                "🐤 현재 단계: " + bird.getStage().getName() + "<br>" +
                "<span style='white-space:nowrap; display:inline-block;'>" +
                bird.getStage().getDescription() + "</span><br><br>" +
                "🌟 포인트: " + pointService.getCurrentPoint(bird.getUsername()) + "점" +
                "</div></html>";
        lblBirdInfo.setText(info);
        birdRenderer.repaint();
    }
}
