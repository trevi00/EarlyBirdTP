package repository.user;

public interface UserRepository {

    User findByUsername(String username);

    void save(User user);

    boolean existsByUsername(Stirng username);

}
