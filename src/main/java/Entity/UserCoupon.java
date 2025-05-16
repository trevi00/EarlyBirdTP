package Entity;

import java.time.LocalDate;

public class UserCoupon {
    /*
    user_id     varchar2(64) references users(id), -- 쿠폰을 소지한 유저 아이디
    coupon_id   varchar2(64) references coupons(id), -- 쿠폰 일련번호
    acquired_at date -- 쿠폰 획득일자
     */

    private String user_id;

    private String coupon_id;

    private LocalDate acquired_at;

    public UserCoupon(String user_id, String coupon_id) {
        this.user_id = user_id;
        this.coupon_id = coupon_id;
        acquired_at = LocalDate.now();
    }

    public UserCoupon(String user_id, String coupon_id, LocalDate acquired_at) {
        this.user_id = user_id;
        this.coupon_id = coupon_id;
        this.acquired_at = acquired_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getAcquired_at() {
        return acquired_at;
    }

    public void setAcquired_at(LocalDate acquired_at) {
        this.acquired_at = acquired_at;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }
}
