package bird.point;

public interface PointService {
    void addPoint(String username, int amount);
    int getCurrentPoint(String username);
    void subtractPoint(String username, int amount);
}
