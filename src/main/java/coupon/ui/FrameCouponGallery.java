package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.CouponPurchase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
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

        // ✅ 구매일 오름차순 정렬 (가장 먼저 산 것 위로)
        history.sort(Comparator.comparing(CouponPurchase::getPurchaseDate));

        String[] columnNames = {"이미지", "쿠폰 이름", "가격", "구매일"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                return Object.class;
            }
        };

        for (CouponPurchase purchase : history) {
            String imagePath = null;
            String couponName = purchase.getCoupon().getName();

            switch (couponName) {
                case "허니브레드":
                    imagePath = "/img/포인트상점/hunny.jpg";
                    break;
                case "프라푸치노":
                    imagePath = "/img/포인트상점/frafuchino.jpg";
                    break;
                case "아메리카노":
                    imagePath = "/img/포인트상점/americano.jpg";
                    break;
                case "콜라":
                    imagePath = "/img/포인트상점/cola.jpg";
                    break;
                default:
            }

            ImageIcon icon = null;
            if (imagePath != null) {
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    Image image = new ImageIcon(imageUrl).getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                } else {
                    System.out.println("이미지 로딩 실패: " + imagePath);
                }
            }

            Object[] row = {
                    icon,
                    couponName,
                    purchase.getCoupon().getPrice(),
                    purchase.getPurchaseDate().toString()
            };

            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.setRowHeight(50);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
