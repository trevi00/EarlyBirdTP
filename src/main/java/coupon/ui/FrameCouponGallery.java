package coupon.ui;

import coupon.controller.CouponController;
import coupon.model.CouponPurchase;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class FrameCouponGallery extends JFrame {

    private final CouponController couponController;
    private final String username;

    // ✅ 쿠폰 이름 - 이미지 경로 매핑
    private static final Map<String, String> couponImageMap = Map.of(
            "허니브레드", "/img/포인트상점/hunny.jpg",
            "프라푸치노", "/img/포인트상점/frafuchino.jpg",
            "아메리카노", "/img/포인트상점/americano.jpg",
            "콜라", "/img/포인트상점/cola.jpg"
    );

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
        history.sort(Comparator.comparing(CouponPurchase::getPurchaseDate)); // 구매일 오름차순 정렬

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(getBackgroundColor());

        for (CouponPurchase purchase : history) {
            String name = purchase.getCoupon().getName();
            int price = purchase.getCoupon().getPrice();
            String dateText = purchase.getPurchaseDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ImageIcon icon = loadCouponImage(name);

            JPanel card = createCouponCard(name, price, dateText, icon);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 여백
            contentPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    // ✅ 쿠폰 카드 생성
    private JPanel createCouponCard(String name, int price, String dateText, ImageIcon icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        card.setMaximumSize(new Dimension(380, 80));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("쿠폰: " + name);
        JLabel priceLabel = new JLabel("가격: " + price + "P");
        JLabel dateLabel = new JLabel("구매일시: " + dateText);

        nameLabel.setFont(getTitleFont());
        priceLabel.setFont(getBodyFont());
        dateLabel.setFont(getBodyFont());

        textPanel.add(nameLabel);
        textPanel.add(priceLabel);
        textPanel.add(dateLabel);

        card.add(imgLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    // ✅ 이미지 로딩
    private ImageIcon loadCouponImage(String name) {
        String path = couponImageMap.getOrDefault(name, null);
        if (path == null) return null;

        URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("⚠ 이미지 로딩 실패: " + path);
            return null;
        }

        Image img = new ImageIcon(url).getImage().getScaledInstance(60, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ✅ 공통 스타일 메서드
    private Font getTitleFont() {
        return new Font("맑은 고딕", Font.BOLD, 14);
    }

    private Font getBodyFont() {
        return new Font("맑은 고딕", Font.PLAIN, 12);
    }

    private Color getBackgroundColor() {
        return new Color(200, 230, 255);
    }
}