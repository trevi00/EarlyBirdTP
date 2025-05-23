package app.ui;

import app.context.EarlyBirdContext;
import attendance.ui.FrameAttendance;
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
 * - 기능 버튼들을 그룹화하고, UI를 정돈하여 UX 향상
 */
public class MainMenuPanel extends JPanel {


    private JLabel pointLabel;

    public MainMenuPanel(EarlyBirdContext context) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(120, 20, 20, 30));

        add(makeButton("출석하기", "/출석하기.png", () -> {
            context.getBirdMessageManager().say("출석 화면으로 이동 중입니다...");
            context.showAttendanceFrame();
        }));

        add(makeButton("ToDo 등록","/Todo등록.png", () -> {
            context.getBirdMessageManager().say("Todo 등록 화면으로 이동 중입니다...");
            new FrameToDo(context.getToDoService(), context.bird, context.getBirdMessageManager(),
                    context.birdService, context.pointService).setVisible(true);
        }));

        add(Box.createVerticalStrut(15));  // 간격

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(120, 50, 20, 30));

        add(makeButton("출석기록 확인", "/출석기록보기.png", () -> {
            context.getBirdMessageManager().say("출석기록 화면으로 이동 중입니다...");
            new FrameAttendanceStats(context.attendanceStatsService, context.getCurrentUsername()).setVisible(true);
        }));

        add(makeButton("ToDo 리스트 확인","/할 일 보기.png", () -> {
            context.getBirdMessageManager().say("ToDo 리스트 목록 화면으로 이동 중입니다...");
            new FrameToDoList(
                    context.getToDoService(),
                    context.getCurrentUsername(),
                    context.getBirdMessageManager()
            ).setVisible(true);
        }));

        add(makeButton("새 보기","/새 보기.png", () -> {
            context.getBirdMessageManager().say("새 상태 화면으로 이동 중입니다...");
            new FrameBird(context.bird, context.birdService, context.getBirdMessageManager(), context.pointService).setVisible(true);
        }));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(120, 10, 20, 30));

        add(makeButton("쿠폰 보관함","/쿠폰 보관함.png", () -> {
            context.getBirdMessageManager().say("쿠폰 갤러리로 이동 중입니다...");
            new FrameCouponGallery(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));

        add(Box.createVerticalStrut(15));  // 간격

        add(makeButton("포인트 상점","/포인트 상점.png", () -> {
            context.getBirdMessageManager().say("포인트 상점으로 이동 중입니다...");
            new FrameCouponStore(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));
    }

    private void refreshPoint(EarlyBirdContext context) {
        int point = context.pointService.getCurrentPoint(context.getCurrentUsername());
        pointLabel.setText("현재 포인트: " + point + "점");
    }

    // 🔧 버튼 생성 유틸
    // 🔧 버튼 생성 유틸 (이미지만 보이게)
    private JButton makeButton(String tooltip, String imagePath, Runnable action) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(imagePath));
            Image img = icon.getImage().getScaledInstance(250, 60, Image.SCALE_SMOOTH); // 원하는 크기로
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + imagePath);
        }

        JButton btn = new JButton();
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(250, 50)); // 버튼 크기 조정
        btn.setMaximumSize(new Dimension(300, 48));
        btn.setMinimumSize(new Dimension(300, 48));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(new Color(240, 248, 255));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); // 테두리 없애기(선택)
        btn.setContentAreaFilled(false); // 배경 없애기(선택)
        btn.setToolTipText(tooltip); // 툴팁으로 설명 제공
        btn.addActionListener(e -> action.run());
        return btn;
    }



    // 🔧 섹션 라벨 생성 유틸
    private JLabel makeSectionLabel(String title) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        return label;
    }
}