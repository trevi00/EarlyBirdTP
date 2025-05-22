package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.Coupon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * [FrameCouponStore]
 * - 사용자가 포인트로 쿠폰을 구매할 수 있는 상점 화면
 */
public class FrameCouponStore extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponStore(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("포인트 상점");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 세로 배치

        List<Coupon> coupons = couponController.getAvailableCoupons();
        for (Coupon coupon : coupons) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel imageLabel = new JLabel();
            String resourcePath = null;

            if (coupon.getName().equals("허니브레드")) {
                resourcePath = "/img/hunny.jpg";
            } else if (coupon.getName().equals("프라푸치노")) {
                resourcePath = "/img/frafuchino.jpg";
            } else if (coupon.getName().equals("아메리카노")) {
                resourcePath = "/img/americano.jpg";
            } else if (coupon.getName().equals("콜라")) {
                resourcePath = "/img/cola.jpg";
            }

            if (resourcePath != null) {
                URL imageUrl = getClass().getResource(resourcePath);
                if (imageUrl != null) {
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image img = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);

                    imageLabel.setIcon(icon);
                    imageLabel.setPreferredSize(new Dimension(80, 60)); // 크기 지정
                } else {
                    System.out.println("❌ 이미지 로딩 실패: " + resourcePath);
                }
            }

            JButton button = new JButton(coupon.getName() + " - " + coupon.getPrice() + "P");
            button.setBackground(Color.WHITE);
            button.addActionListener(e -> handlePurchase(coupon));

            // ✅ 이미지 + 버튼을 하나의 행 패널에 넣는다
            rowPanel.add(imageLabel);
            rowPanel.add(Box.createRigidArea(new Dimension(10, 0))); // 간격
            rowPanel.add(button);

            // ✅ 행 전체를 메인 패널에 넣는다
            panel.add(rowPanel);
        }

        add(panel);
    }
    private void handlePurchase(Coupon coupon) {
        boolean success = couponController.purchase(username, coupon);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    coupon.getName() + " 쿠폰을 구매했습니다!",
                    "구매 성공",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "포인트가 부족합니다.",
                    "구매 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
