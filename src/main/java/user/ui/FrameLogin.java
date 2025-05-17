package user.ui;

import app.ui.FrameMain;
import user.handler.LoginHandler;
import user.service.UserService;

import javax.swing.*;
import java.awt.*;

/**
 * [FrameLogin]
 * - 사용자 로그인 UI
 * - 로그인 성공 시 username을 FrameMain에 전달
 */
public class FrameLogin extends JFrame {

    private final LoginHandler loginHandler;

    public FrameLogin(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;

        setTitle("로그인");
        setSize(300, 200);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 입력 필드
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // 버튼
        JButton btnLogin = new JButton("로그인");
        JButton btnRegister = new JButton("회원가입");

        add(new JLabel("아이디:"));
        add(usernameField);
        add(new JLabel("비밀번호:"));
        add(passwordField);
        add(btnLogin);
        add(btnRegister);

        // ✅ 로그인 버튼 이벤트
        btnLogin.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (loginHandler.tryLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "로그인 성공");

                dispose(); // 로그인 창 닫고

                // ✅ 로그인된 사용자 이름을 FrameMain에 전달
                new FrameMain(username);
            } else {
                JOptionPane.showMessageDialog(this, "로그인 실패. 아이디 또는 비밀번호 확인");
            }
        });

        // 회원가입 버튼 이벤트
        btnRegister.addActionListener(e -> {
            UserService userService = loginHandler.getUserService();
            new RegisterFrame(userService);
        });

        setVisible(true);
    }
}
