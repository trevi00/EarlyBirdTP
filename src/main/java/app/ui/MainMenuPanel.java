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
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900, 600));
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        // 왼쪽 상단: 출석 체크 + 현황
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(150, 100, 0, 80);
        add(makeVerticalPanel(
                makeButton("출석 체크", "/img/메인메뉴 디자인/출석 체크.png", () ->
                        context.showAttendanceFrame()),
                makeButton("출석 현황", "/img/메인메뉴 디자인/출석 현황.png", () ->
                        new FrameAttendanceStats(context.attendanceStatsService, context.getCurrentUsername()).setVisible(true))
        ), gbc);

        // 오른쪽 상단: 마이 얼리버드 ← 이동됨
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(100, 0, 0, 90);
        add(makeCenteredPanel(
                makeButton("마이 얼리버드", "/img/메인메뉴 디자인/마이 얼리버드.png", () ->
                        new FrameBird(context.bird, context.birdService, context.getBirdMessageManager(), context.pointService).setVisible(true))
        ), gbc);

// 왼쪽 하단: To Do 등록 + 보기 ← 이동됨
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(200, 100, 100, 80);
        add(makeVerticalPanel(
                makeButton("To Do 등록", "/img/메인메뉴 디자인/To Do 등록.png", () ->
                        new FrameToDo(context.getToDoService(), context.bird, context.getBirdMessageManager()).setVisible(true)),
                makeButton("To Do 보기", "/img/메인메뉴 디자인/To Do 보기.png", () ->
                        new FrameToDoList(context.getToDoService(), context.getCurrentUsername(), context.getBirdMessageManager()).setVisible(true))
        ), gbc);

        // 오른쪽 하단: 쿠폰 보관함 + 포인트 상점
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(100, 0, 0, 90);
        add(makeVerticalPanel(
                makeButton("쿠폰 보관함", "/img/메인메뉴 디자인/쿠폰 보관함.png", () ->
                        new FrameCouponGallery(context.getCouponController(), context.getCurrentUsername()).setVisible(true)),
                makeButton("포인트 상점", "/img/메인메뉴 디자인/포인트 상점.png", () ->
                        new FrameCouponStore(context.getCouponController(), context.getCurrentUsername()).setVisible(true))
        ), gbc);
    }

    private JPanel makeVerticalPanel(JButton top, JButton bottom) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(top);
        panel.add(Box.createVerticalStrut(12));
        panel.add(bottom);
        return panel;
    }

    private JPanel makeCenteredPanel(JButton button) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(button);
        return panel;
    }

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
}
