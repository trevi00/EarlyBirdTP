package prototype.EarlyBird;
import prototype.Bird.BirdManagement;
import prototype.CheckUser.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EarlyBird {
    public static void main(String[] args) {
        // 바탕화면 사진 경로(본인 환경에 맞게 수정)
        String imagePath = "C:\\두두둥장.png";
        // Mac 예시: String imagePath = "/Users/사용자이름/Desktop/myphoto.jpg";

        int frameWidth = 600;
        int frameHeight = 600;

        JFrame frame = new JFrame("early bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null); // 화면 중앙

        // 배경 패널 생성
        JPanel backgroundPanel = new JPanel() {
            Image background = new ImageIcon(imagePath).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        // 버튼 이미지 파일 경로 (원하는 경로로 수정하세요)
        String[] btnImagePaths = {
                "C://출석 체크.png",
                "C://날씨 확인.png",
                "C://일기 작성.png",
                "C://포인트 획득.png",
                "C://새 키우기.png"
        };

        // 버튼 위치 및 크기(예시)
        int[][] btnBounds = {
                {200, 60, 200, 100},   // 출석 체크
                {200, 140, 200, 100},  // 날씨 확인
                {200, 220, 200, 100},  // 일기 작성
                {200, 300, 200, 100},  // 포인트 획득
                {200, 380, 200, 100}   // 새 키우기
        };

        JButton[] btns = new JButton[5];

        for (int i = 0; i < btns.length; i++) {
            ImageIcon icon = new ImageIcon(btnImagePaths[i]);
            // 이미지 크기 조정
            Image scaledImg = icon.getImage().getScaledInstance(btnBounds[i][2], btnBounds[i][3], Image.SCALE_SMOOTH);
            btns[i] = new JButton(new ImageIcon(scaledImg));
            btns[i].setBounds(btnBounds[i][0], btnBounds[i][1], btnBounds[i][2], btnBounds[i][3]);
            btns[i].setContentAreaFilled(false); // 배경 투명
            btns[i].setBorderPainted(false);     // 테두리 제거
            btns[i].setFocusPainted(false);      // 포커스 표시 제거
            btns[i].setText("");                 // 텍스트 제거

            backgroundPanel.add(btns[i]);
        }

        // Exit 버튼은 텍스트 버튼으로 유지
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(230, 480, 120, 40);
        backgroundPanel.add(exitBtn);
        exitBtn.addActionListener(e -> System.exit(0));

        // 각 버튼에 ActionListener 추가 (팝업 중앙 배치)
        for (int i = 0; i < btns.length; i++) {
            final int idx = i;
            btns[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch(idx){
                        case 0:
                            User user1 = new User("최대호");
                            BirdManagement management = new BirdManagement();

                            user1.tryCheck();
                            user1.printDate();
                            System.out.println("이번달출석 횟수. : "+user1.MonthCount());
                            management.addPoint(user1.getPoint());
                            management.currentBird();
                            break;
                        default:
                            JDialog popup = new JDialog(frame, "새 창", true);
                            popup.setSize(300, 200);
                            popup.setLayout(null);

                            // 화면 중앙에 위치
                            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                            int x = (screenSize.width - popup.getWidth()) / 2;
                            int y = (screenSize.height - popup.getHeight()) / 2;
                            popup.setLocation(x, y);

                            JLabel label = new JLabel((idx + 1) + "EarlyBird", SwingConstants.CENTER);
                            label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
                            label.setBounds(50, 70, 200, 40);
                            popup.add(label);

                            popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            popup.setVisible(true);
                    }

                }
            });
        }

        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }
}
