package team_prototype.Bird;

public class BirdManagement {

    private final Bird bird;
    
    public BirdManagement(){
        this.bird= new Bird();
    }
    
    public void addPoint(int amount){
        if (amount > 0) {
            bird.setPoint(bird.getPoint() + amount);
            breedingStage();
        }
    }
    
    private void breedingStage(){
        int points=bird.getPoint();
        if (points < 10) {
            bird.setStage(BirdStatus.EGG);
        } else if (points < 20) {
            bird.setStage(BirdStatus.INFANCY);
        } else if (points < 30) {
            bird.setStage(BirdStatus.MATURITY);
        } else {
            bird.setStage(BirdStatus.ADULT);
        }
    }
    
    public String currentBird(){
        StringBuilder sb = new StringBuilder();
        sb.append("현재 당신의 새는 ").append(bird.getStage().getGrowStage()).append("입니다.\n");
        sb.append("현재 누적 포인트는 ").append(bird.getPoint()).append("점 입니다.\n");
        
        sb.append("\n오늘의 조언\n");
        for(String msg:bird.getStage().getRandomMessages(1)){
            sb.append(msg);
        }
        return sb.toString();
    }

}
