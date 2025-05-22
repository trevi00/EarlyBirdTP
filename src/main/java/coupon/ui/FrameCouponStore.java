package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.Coupon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrameCouponStore extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponStore(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("포인트 상점");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CouponUIUtil.getBackgroundColor());

        List<Coupon> coupons = couponController.getAvailableCoupons();
        for (Coupon coupon : coupons) {
            JPanel card = CouponUIUtil.createCouponCard(coupon, this::handlePurchase);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            contentPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
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