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

public class MainMenuPanel extends JPanel {

    public MainMenuPanel(EarlyBirdContext context) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 30)); // 스크롤 없는 고정 크기
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // 버튼 그룹 생성
        add(makeRow(
                makeButton("출석하기", "/img/메인화면/출석하기.png", () -> {
                    context.getBirdMessageManager().say("출석 화면으로 이동 중입니다...");
                    context.showAttendanceFrame();
                }),
                makeButton("ToDo 등록", "/img/메인화면/Todo등록.png", () -> {
                    context.getBirdMessageManager().say("Todo 등록 화면으로 이동 중입니다...");
                    new FrameToDo(context.getToDoService(), context.bird, context.getBirdMessageManager()).setVisible(true);
                })
        ));

        add(makeRow(
                makeButton("출석기록보기", "/img/메인화면/출석기록보기.png", () -> {
                    context.getBirdMessageManager().say("출석기록 화면으로 이동 중입니다...");
                    new FrameAttendanceStats(context.attendanceStatsService, context.getCurrentUsername()).setVisible(true);
                }),
                makeButton("ToDo 보기", "/img/메인화면/할 일 보기.png", () -> {
                    context.getBirdMessageManager().say("ToDo 리스트 목록 화면으로 이동 중입니다...");
                    new FrameToDoList(
                            context.getToDoService(),
                            context.getCurrentUsername(),
                            context.getBirdMessageManager()
                    ).setVisible(true);
                })
        ));

        add(makeRow(
                makeButton("쿠폰보관함", "/img/메인화면/쿠폰 보관함.png", () -> {
                    context.getBirdMessageManager().say("쿠폰 갤러리로 이동 중입니다...");
                    new FrameCouponGallery(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
                }),
                makeButton("포인트상점", "/img/메인화면/포인트 상점.png", () -> {
                    context.getBirdMessageManager().say("포인트 상점으로 이동 중입니다...");
                    new FrameCouponStore(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
                })
        ));

        // 중앙 단독 "새 보기"
        add(//centeredRow(
                makeButton("새 보기", "/img/메인화면/새 보기.png", () -> {
                    context.getBirdMessageManager().say("새 상태 화면으로 이동 중입니다...");
                    new FrameBird(context.bird, context.birdService, context.getBirdMessageManager(), context.pointService).setVisible(true);
                }));
        //));


    }

    // 🔧 공통 버튼 생성 유틸
    private JButton makeButton(String tooltip, String imagePath, Runnable action) {
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(imagePath));
            Image img = icon.getImage().getScaledInstance(300, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + imagePath);
        }

        JButton btn = new JButton();
        btn.setIcon(icon);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setToolTipText(tooltip);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    // 🔧 두 버튼 가로 정렬 패널
    private JPanel makeRow(JButton left, JButton right) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 12));
        panel.setOpaque(false);
        panel.add(left);
        panel.add(right);
        return panel;
    }

    // 🔧 단일 버튼 중앙 정렬 패널
    private JPanel centeredRow(JButton center) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 12));
        panel.setOpaque(false);
        panel.add(center);
        return panel;
    }
}
