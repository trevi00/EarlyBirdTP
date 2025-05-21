package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.CouponPurchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.net.URL;
/**
 * [FrameCouponGallery]
 * - 유저가 구매한 쿠폰 내역을 보여주는 보관함 화면
 */
public class FrameCouponGallery extends JFrame {

    private final CouponController couponController;
    private final String username;

    public FrameCouponGallery(CouponController couponController, String username) {
        this.couponController = couponController;
        this.username = username;

        setTitle("쿠폰 보관함");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        List<CouponPurchase> history = couponController.getPurchaseHistory(username);

        // ✅ 이미지 컬럼 추가
        String[] columnNames = {"이미지", "쿠폰 이름", "가격", "구매일"};

        // ✅ 이미지 표시 위해 getColumnClass 오버라이드
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                return Object.class;
            }
        };

        for (CouponPurchase purchase : history) {
            // 쿠폰 이름에 따라 이미지 선택
            String imagePath = null;
            String couponName = purchase.getCoupon().getName();

            if (couponName.equals("싸이버거")) {
                imagePath = "/img/thighburger.jpg";
            } else if (couponName.equals("박카스")) {
                imagePath = "/img/baccas.jpg";
            }

            ImageIcon icon = null;
            if (imagePath != null) {
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    Image image = new ImageIcon(imageUrl).getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                } else {
                    System.out.println("⚠ 이미지 로딩 실패: " + imagePath);
                }
            }

            Object[] row = {
                    icon, // 이미지
                    couponName,
                    purchase.getCoupon().getPrice(),
                    purchase.getPurchaseDate().toString()
            };

            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(50); // ✅ 이미지 보이도록 행 높이 조정

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
