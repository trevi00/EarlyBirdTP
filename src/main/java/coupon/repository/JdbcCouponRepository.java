package coupon.repository;

import coupon.model.Coupon;
import coupon.model.CouponPurchase;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCouponRepository implements CouponRepository {

    private final Connection conn;

    public JdbcCouponRepository(Connection conn) {
        this.conn = conn;
    }

    /**
     * 판매 중인 모든 쿠폰을 조회
     */
    @Override
    public List<Coupon> findAllAvailable() {
        List<Coupon> list = new ArrayList<>();
        String sql = "SELECT ID, NAME, COST FROM COUPONS";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Coupon(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getInt("COST")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 유저가 쿠폰을 구매한 내역을 저장
     */
    @Override
    public void savePurchase(String username, Coupon coupon) {
        String sql = "INSERT INTO COUPON_PURCHASES (id, username, coupon_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, username);
            stmt.setString(3, coupon.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 유저의 구매 내역을 조회
     */
    @Override
    public List<CouponPurchase> findPurchaseHistoryByUsername(String username) {
        List<CouponPurchase> list = new ArrayList<>();
        String sql = "SELECT c.ID, c.NAME, c.COST, h.PURCHASE_DATE " +
                "FROM COUPON_PURCHASES h JOIN COUPONS c ON h.COUPON_ID = c.ID " +
                "WHERE h.USERNAME = ? ORDER BY h.PURCHASE_DATE DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                int cnt = 0;
                while (rs.next()) {
                    ++cnt;
                    Coupon coupon = new Coupon(
                            rs.getString("ID"),
                            rs.getString("NAME"),
                            rs.getInt("COST")
                    );
                    Timestamp purchaseDate = rs.getTimestamp("PURCHASE_DATE");
                    list.add(new CouponPurchase(coupon, purchaseDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
