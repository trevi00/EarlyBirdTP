package repository.user;

import Entity.User;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    boolean existsByUsername(String username);

}
