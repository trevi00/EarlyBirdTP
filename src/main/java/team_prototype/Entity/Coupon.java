package team_prototype.Entity;

public class Coupon {
    /*
    id          varchar2(64) primary key, -- 쿠폰 일련번호(아이디)
    name        varchar2(100), -- 쿠폰 이름
    description varchar2(200), -- 쿠폰 설명
    cost        number -- 쿠폰 가격
     */

    private String id;

    private String name;

    private String description;

    private int cost;

    public Coupon(String id, String name, String description, int cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
