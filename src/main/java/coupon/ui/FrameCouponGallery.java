package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.CouponPurchase;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class FrameCouponGallery extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponGallery(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("쿠폰 보관함");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        List<CouponPurchase> history = couponController.getPurchaseHistory(username);
        history.sort(Comparator.comparing(CouponPurchase::getPurchaseDate));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CouponUIUtil.getBackgroundColor());

        for (CouponPurchase purchase : history) {
            String name = purchase.getCoupon().getName();
            int price = purchase.getCoupon().getPrice();
            String dateText = purchase.getPurchaseDate().toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ImageIcon icon = CouponUIUtil.loadCouponImage(name, 200, 140);

            JPanel card = CouponUIUtil.createCouponCard(name, price, dateText, icon);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            contentPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }
}