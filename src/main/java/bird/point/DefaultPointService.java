package bird.point;

import bird.repository.PointRepository;

public class DefaultPointService implements PointService {

    private final PointRepository pointRepository;

    public DefaultPointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public void addPoint(String username, int amount) {
        int current = pointRepository.findPointByUsername(username);
        pointRepository.updatePoint(username, current + amount);
    }

    @Override
    public int getCurrentPoint(String username) {
        return pointRepository.findPointByUsername(username);
    }

    @Override
    public void subtractPoint(String username, int amount) {
        int current = pointRepository.findPointByUsername(username);
        int updated = Math.max(current - amount, 0);
        pointRepository.updatePoint(username, updated);
    }
}
