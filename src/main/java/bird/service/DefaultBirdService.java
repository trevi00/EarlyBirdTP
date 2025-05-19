package bird.service;

import bird.model.Bird;
import bird.point.PointService;
import bird.repository.BirdRepository;

public class DefaultBirdService implements BirdService {

    private final StageEvolutionPolicy evolutionPolicy;
    private final BirdRepository birdRepository;
    private final PointService pointService;

    public DefaultBirdService(StageEvolutionPolicy evolutionPolicy,
                              BirdRepository birdRepository,
                              PointService pointService) {
        this.evolutionPolicy = evolutionPolicy;
        this.birdRepository = birdRepository;
        this.pointService = pointService;
    }

    @Override
    public boolean canEvolve(Bird bird) {
        int point = pointService.getCurrentPoint(bird.getUsername());
        int required = bird.getStage().getNeedPoint();
        return bird.getStage().getNextStage() != null && point >= required;
    }

    @Override
    public void evolve(Bird bird) {
        if (!canEvolve(bird)) return;

        int cost = bird.getStage().getNeedPoint();

        // 1. 포인트 차감 (DB 기준)
        pointService.subtractPoint(bird.getUsername(), cost);

        // 2. bird 객체와 동기화
        bird.addPoint(-cost);

        // 3. 단계 진화
        evolutionPolicy.evolve(bird);

        // 4. 저장
        birdRepository.save(bird);
    }

    @Override
    public int getPoint(String username) {
        return pointService.getCurrentPoint(username); // ✅ DB 기준으로 조회
    }

    @Override
    public void usePoint(String username, int point) {
        pointService.subtractPoint(username, point);   // ✅ DB에서 차감
    }
}
