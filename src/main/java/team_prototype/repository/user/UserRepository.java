package team_prototype.repository.user;

import team_prototype.Entity.User;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    boolean existsByUsername(String username);

}
