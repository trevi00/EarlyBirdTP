package coupon.ui;

import coupon.model.Coupon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

public class CouponUIUtil {

    private static final Map<String, String> couponImageMap = Map.of(
            "허니브레드", "/img/포인트상점/hunny.png",
            "프라푸치노", "/img/포인트상점/frafuchino.png",
            "아메리카노", "/img/포인트상점/americano.png",
            "콜라", "/img/포인트상점/cola.png"
    );

    public static ImageIcon loadCouponImage(String name, int width, int height) {
        String path = couponImageMap.getOrDefault(name, null);
        if (path == null) return null;

        URL url = CouponUIUtil.class.getResource(path);
        if (url == null) {
            System.out.println("⚠ 이미지 로딩 실패: " + path);
            return null;
        }

        Image img = new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static JPanel createCouponCard(String name, int price, String dateText, ImageIcon icon) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(420, 100));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel = new JLabel(icon);
        imgLabel.setPreferredSize(new Dimension(200, 140));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("쿠폰: " + name);
        nameLabel.setFont(getTitleFont());

        JLabel priceLabel = new JLabel("가격: " + price + "P");
        priceLabel.setFont(getBodyFont());

        JLabel dateLabel = new JLabel("구매일시: " + dateText);
        dateLabel.setFont(getBodyFont());

        textPanel.add(nameLabel);
        textPanel.add(priceLabel);
        textPanel.add(dateLabel);

        card.add(imgLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    public static JPanel createStoreCard(Coupon coupon, Consumer<Coupon> onPurchase) {
        ImageIcon icon = loadCouponImage(coupon.getName(), 200, 140);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(420, 100));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel = new JLabel(icon);
        imgLabel.setPreferredSize(new Dimension(200, 140)); // 기존 100x80 → 120x100

        // 👉 텍스트 + 버튼 패널 (수직 정렬)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(coupon.getName());
        nameLabel.setFont(getTitleFont());
        nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JButton buyButton = new JButton(coupon.getPrice() + "P로 구매");
        buyButton.setFont(getBodyFont());
        buyButton.setBackground(new Color(100, 180, 255));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // 👉 오른쪽 정렬
        buyButton.addActionListener(e -> onPurchase.accept(coupon));

        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(buyButton);

        card.add(imgLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.EAST); // 👉 동쪽에 배치

        return card;
    }

    public static Font getTitleFont() {
        return new Font("맑은 고딕", Font.BOLD, 14);
    }

    public static Font getBodyFont() {
        return new Font("맑은 고딕", Font.PLAIN, 12);
    }

    public static Color getBackgroundColor() {
        return new Color(200, 230, 255);
    }
}