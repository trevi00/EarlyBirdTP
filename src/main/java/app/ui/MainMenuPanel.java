package app.ui;

import app.context.EarlyBirdContext;
import attendance.ui.FrameAttendanceStats;
import bird.ui.FrameBird;
import coupon.ui.FrameCouponGallery;
import coupon.ui.FrameCouponStore;
import todo.ui.FrameToDo;
import todo.ui.FrameToDoList;

import javax.swing.*;
import java.awt.*;

/**
 * [MainMenuPanel]
 * - 좌측: 기록하기 (출석하기, ToDo 등록)
 * - 우측: 보기 (출석기록, ToDo 리스트, 새 보기)
 * - 하단: 포인트 상점 & 쿠폰 보관함 (가로 배치)
 */
public class MainMenuPanel extends JPanel {

    public MainMenuPanel(EarlyBirdContext context) {
        setLayout(new BorderLayout());

        // 좌측 - 기록하기
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        leftPanel.add(makeSectionLabel("기록하기"));
        leftPanel.add(makeButton("출석하기", "/img/메인화면/출석하기.png", () -> {
            context.getBirdMessageManager().say("출석 화면으로 이동 중입니다...");
            context.showAttendanceFrame();
        }));

        leftPanel.add(makeButton("ToDo 등록", "/img/메인화면/Todo등록.png", () -> {
            context.getBirdMessageManager().say("Todo 등록 화면으로 이동 중입니다...");
            new FrameToDo(context.getToDoService(), context.bird, context.getBirdMessageManager()).setVisible(true);
        }));

        // 우측 - 보기
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        rightPanel.add(makeSectionLabel("보기"));
        rightPanel.add(makeButton("출석기록 확인", "/img/메인화면/출석기록보기.png", () -> {
            context.getBirdMessageManager().say("출석기록 화면으로 이동 중입니다...");
            new FrameAttendanceStats(context.attendanceStatsService, context.getCurrentUsername()).setVisible(true);
        }));

        rightPanel.add(makeButton("ToDo 리스트 확인", "/img/메인화면/할 일 보기.png", () -> {
            context.getBirdMessageManager().say("ToDo 리스트 목록 화면으로 이동 중입니다...");
            new FrameToDoList(context.getToDoService(), context.getCurrentUsername(), context.getBirdMessageManager()).setVisible(true);
        }));

        rightPanel.add(makeButton("새 보기", "/img/메인화면/새 보기.png", () -> {
            context.getBirdMessageManager().say("새 상태 화면으로 이동 중입니다...");
            new FrameBird(context.bird, context.birdService, context.getBirdMessageManager(), context.pointService).setVisible(true);
        }));

        // 하단 - 포인트 영역
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bottomPanel.add(makeSmallButton("포인트 상점", "/img/메인화면/포인트 상점.png", () -> {
            context.getBirdMessageManager().say("포인트 상점으로 이동 중입니다...");
            new FrameCouponStore(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));

        bottomPanel.add(makeSmallButton("쿠폰 보관함", "/img/메인화면/쿠폰 보관함.png", () -> {
            context.getBirdMessageManager().say("쿠폰 갤러리로 이동 중입니다...");
            new FrameCouponGallery(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));

        // 전체 배치
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 🔧 일반 버튼 생성 (150x40)
    private JButton makeButton(String tooltip, String imagePath, Runnable action) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(imagePath));
            Image img = icon.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + imagePath);
        }

        JButton btn = new JButton();
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setMaximumSize(new Dimension(300, 48));
        btn.setMinimumSize(new Dimension(300, 48));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(240, 248, 255));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setToolTipText(tooltip);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    // 🔧 작은 버튼 생성 (140x50, 하단용)
    private JButton makeSmallButton(String tooltip, String imagePath, Runnable action) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(imagePath));
            Image img = icon.getImage().getScaledInstance(140, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + imagePath);
        }

        JButton btn = new JButton();
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(140, 50));
        btn.setMaximumSize(new Dimension(140, 50));
        btn.setMinimumSize(new Dimension(140, 50));
        btn.setToolTipText(tooltip);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        return btn;
    }

    // 🔧 섹션 제목 라벨
    private JLabel makeSectionLabel(String title) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        return label;
    }
}
