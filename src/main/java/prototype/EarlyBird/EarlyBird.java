package prototype.EarlyBird;
import prototype.Bird.BirdManagement;
import prototype.CheckUser.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EarlyBird {
    public static void main(String[] args) {
        // 바탕화면 사진 경로(본인 환경에 맞게 수정)
        String imagePath = "C:\\Users\\사용자이름\\Desktop\\두두둥장.jpg";
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

        // 버튼 5개 생성 및 배치
        String[] btnNames = {"출석 체크", "날씨 확인", "todo작성", "todo확인", "새 키우기"};
        JButton[] btns = new JButton[5];

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JButton(btnNames[i]);
            btns[i].setFont(new Font("맑은 고딕", Font.PLAIN, 16));
            btns[i].setBounds(230, 100 + i * 60, 120, 40);
            backgroundPanel.add(btns[i]);
        }

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(230, 420, 120, 40);
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
