package bird.repository;

public interface PointRepository {
    int findPointByUsername(String username);
    void updatePoint(String username, int newPoint);
}
