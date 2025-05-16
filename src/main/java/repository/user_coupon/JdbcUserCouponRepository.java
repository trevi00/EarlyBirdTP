package repository.user_coupon;

import database.DatabaseConnection;

import java.sql.Connection;

public class JdbcUserCouponRepository implements UserCouponRepository {

    final Connection connection = DatabaseConnection.getConnection();

}
