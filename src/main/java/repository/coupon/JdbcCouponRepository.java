package repository.coupon;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcCouponRepository implements CouponRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
