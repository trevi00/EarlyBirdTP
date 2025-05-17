package team_prototype.repository.user_coupon;

import team_prototype.database.DatabaseConnection;

import java.sql.Connection;

public class JdbcUserCouponRepository implements UserCouponRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
