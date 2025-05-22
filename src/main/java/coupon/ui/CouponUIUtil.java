package coupon.ui;

import coupon.model.Coupon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.function.Consumer;

public class CouponUIUtil {

    // 이미지 경로 매핑
    private static final Map<String, String> couponImageMap = Map.of(
            "허니브레드", "/img/포인트상점/hunny.jpg",
            "프라푸치노", "/img/포인트상점/frafuchino.jpg",
            "아메리카노", "/img/포인트상점/americano.jpg",
            "콜라", "/img/포인트상점/cola.jpg"
    );

    // 이미지 로딩
    public static ImageIcon loadCouponImage(String name) {
        String path = couponImageMap.getOrDefault(name, null);
        if (path == null) return null;

        URL url = CouponUIUtil.class.getResource(path);
        if (url == null) {
            System.out.println("⚠ 이미지 로딩 실패: " + path);
            return null;
        }

        Image img = new ImageIcon(url).getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // 카드 생성
    public static JPanel createCouponCard(Coupon coupon, Consumer<Coupon> onPurchase) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        card.setMaximumSize(new Dimension(380, 80));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel = new JLabel(loadCouponImage(coupon.getName()));
        imgLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(coupon.getName());
        nameLabel.setFont(getTitleFont());

        JButton buyButton = new JButton(coupon.getPrice() + "P로 구매");
        buyButton.setFont(getBodyFont());
        buyButton.setBackground(getButtonColor());
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.addActionListener(e -> onPurchase.accept(coupon));

        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(buyButton);

        card.add(imgLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    // 스타일
    public static Font getTitleFont() {
        return new Font("맑은 고딕", Font.BOLD, 14);
    }

    public static Font getBodyFont() {
        return new Font("맑은 고딕", Font.PLAIN, 12);
    }

    public static Color getBackgroundColor() {
        return new Color(200, 230, 255);
    }

    public static Color getButtonColor() {
        return new Color(100, 180, 255);
    }
}