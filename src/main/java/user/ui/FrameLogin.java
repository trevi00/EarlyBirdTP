package user.ui;

import user.handler.LoginHandler;
import user.service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class FrameLogin extends JFrame {

    public FrameLogin(LoginHandler loginHandler) {
        setTitle("로그인");
        setSize(400, 570);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230)); // 🌤️ 연한 하늘색

        // 🐦 상단 새 이미지
        JLabel birdLabel = new JLabel();
        try {
            URL imageUrl = getClass().getResource("/img/로그인/bird.png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                birdLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                birdLabel.setText("사진 없음");
            }
        } catch (Exception e) {
            birdLabel.setText("사진 없음");
        }
        birdLabel.setBounds(110, 10, 180, 180);
        add(birdLabel);

        // ⬜ 둥근 흰색 카드 패널
        RoundedPanel cardPanel = new RoundedPanel(30);
        cardPanel.setOpaque(false);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(null);
        cardPanel.setBounds(50, 150, 300, 320);
        add(cardPanel);

        // 🔤 아이디 입력
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        idLabel.setBounds(30, 40, 240, 20);
        cardPanel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(30, 65, 240, 40);
        idField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        idField.setBorder(new LineBorder(Color.GRAY, 2));
        cardPanel.add(idField);

        // 🔒 비밀번호 입력
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        pwLabel.setBounds(30, 115, 240, 20);
        cardPanel.add(pwLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(30, 140, 240, 40);
        pwField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        pwField.setBorder(new LineBorder(Color.GRAY, 2));
        cardPanel.add(pwField);

        // 🔸 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBounds(20, 210, 120, 45);
        styleButton(loginBtn, new Color(255, 153, 51), new Color(255, 130, 0));
        cardPanel.add(loginBtn);

        // 🔸 회원가입 버튼
        JButton joinBtn = new JButton("회원가입");
        joinBtn.setBounds(160, 210, 120, 45);
        styleButton(joinBtn, new Color(255, 99, 71), new Color(255, 80, 50));
        cardPanel.add(joinBtn);

        // ✅ 로그인 기능 (임시 메시지)
        loginBtn.addActionListener(e -> {
            String username = idField.getText().trim();
            String password = new String(pwField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = loginHandler.tryLogin(username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "로그인 성공!", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 로그인 창 닫기

                // 메인 화면으로 이동
                new app.ui.FrameMain(username); // 또는 new FrameMain(SessionManager.getCurrentUser()) 등
            } else {
                JOptionPane.showMessageDialog(this, "로그인 실패. 아이디 또는 비밀번호를 확인해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        });



        // ✅ 회원가입 창 이동
        joinBtn.addActionListener(e -> {
            UserService userService = loginHandler.getUserService();
            new RegisterFrame(userService);
        });

        setVisible(true);
    }

    // 🎨 버튼 스타일 및 호버 효과 적용
    private void styleButton(JButton button, Color baseColor, Color hoverColor) {
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new LineBorder(baseColor, 0, true));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }
}
