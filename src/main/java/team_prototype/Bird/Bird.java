package team_prototype.Bird;

public class Bird {
 
    private BirdStatus stage;
    private int point;
    
    public Bird(){
        this.stage=BirdStatus.EGG;
        this.point=0;
    }
    
    public BirdStatus getStage() {
        return stage;
    }
    
    public void setStage(BirdStatus stage) {
        this.stage = stage;
    }
    
    public int getPoint() {
        return point;
    }
    
    public void setPoint(int point) {
        this.point = point;
    }
    
}
