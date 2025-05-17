package team_prototype.repository.user;

import team_prototype.Entity.User;
import team_prototype.database.DatabaseConnection;

import java.sql.Connection;

public class JdbcUserRepository implements UserRepository {

    final Connection connection = DatabaseConnection.getConnection();

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}
