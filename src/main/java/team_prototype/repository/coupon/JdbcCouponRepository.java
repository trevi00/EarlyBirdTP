package team_prototype.repository.coupon;

import team_prototype.database.DatabaseConnection;

import java.sql.Connection;

public class JdbcCouponRepository implements CouponRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
