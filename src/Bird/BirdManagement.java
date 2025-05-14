package Bird;

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
    
    public void currentBird(){
        System.out.println("현재 당신의 새는 "+bird.getStage().getGrowStage()+"입니다.");
        System.out.println("현재 당신의 포인트는 "+bird.getPoint()+"점 입니다.");
        
        System.out.println("\n오늘의 조언");
        for(String msg:bird.getStage().getRandomMessages(1)){
            System.out.println(msg);
        }
    }

}
