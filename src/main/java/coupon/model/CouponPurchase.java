package coupon.model;

import java.sql.Timestamp;

public class CouponPurchase {
    private final Coupon coupon;
    private final Timestamp purchaseDate;

    public CouponPurchase(Coupon coupon, Timestamp purchaseDate) {
        this.coupon = coupon;
        this.purchaseDate = purchaseDate;
    }

    public Coupon getCoupon() { return coupon; }
    public Timestamp getPurchaseDate() { return purchaseDate; }
}

