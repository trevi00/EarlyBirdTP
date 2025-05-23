package user.ui;

import user.service.UserService;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

public class RegisterFrame extends JFrame {

    public RegisterFrame(UserService userService) {
        setTitle("회원가입");
        setSize(400, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230)); // 🌤️ 연한 하늘색 배경

        // 🐦 새 이미지 (중앙 배치)
        JLabel birdLabel = new JLabel();
        try {
            URL imageUrl = getClass().getResource("/img/회원가입/bird2.png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
                birdLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                birdLabel.setText("[새 이미지 없음]");
            }
        } catch (Exception e) {
            birdLabel.setText("[이미지 오류]");
        }
        birdLabel.setBounds((400 - 140) / 2, 10, 140, 140);
        add(birdLabel);

        // ⬜ 카드 패널
        RoundedPanel cardPanel = new RoundedPanel(30);
        cardPanel.setOpaque(false);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setLayout(null);
        cardPanel.setBounds(50, 150, 300, 300);
        add(cardPanel);

        // 🔤 아이디 입력
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        idLabel.setBounds(30, 20, 240, 20);
        cardPanel.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(30, 45, 240, 35);
        idField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        idField.setBorder(new LineBorder(Color.GRAY, 2));
        cardPanel.add(idField);

        // 🔒 비밀번호 입력
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        pwLabel.setBounds(30, 85, 240, 20);
        cardPanel.add(pwLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(30, 110, 240, 35);
        pwField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        pwField.setBorder(new LineBorder(Color.GRAY, 2));
        cardPanel.add(pwField);

        // 👤 닉네임 입력
        JLabel nickLabel = new JLabel("닉네임");
        nickLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        nickLabel.setBounds(30, 150, 240, 20);
        cardPanel.add(nickLabel);

        JTextField nickField = new JTextField();
        nickField.setBounds(30, 175, 240, 35);
        nickField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        nickField.setBorder(new LineBorder(Color.GRAY, 2));
        cardPanel.add(nickField);

        // ✅ 가입 버튼
        JButton joinBtn = new JButton("가입하기");
        joinBtn.setBounds(90, 230, 120, 40);
        joinBtn.setBackground(new Color(255, 136, 51));
        joinBtn.setForeground(Color.WHITE);
        joinBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        joinBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        joinBtn.setBorder(new LineBorder(new Color(255, 136, 51), 0, true));
        joinBtn.setFocusPainted(false);
        joinBtn.setOpaque(true);
        joinBtn.setContentAreaFilled(true);
        cardPanel.add(joinBtn);

        // 🎯 버튼 이벤트
        joinBtn.addActionListener(e -> {
            String username = idField.getText().trim();
            String password = new String(pwField.getPassword());
            String nickname = nickField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
                return;
            }

            boolean success = userService.register(username, password, nickname);
            if (success) {
                JOptionPane.showMessageDialog(this, "회원가입 완료!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.");
            }
        });

        setVisible(true);
    }
}
