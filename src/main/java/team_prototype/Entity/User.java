package team_prototype.Entity;

public class User {
    /*
    id            varchar2(64) primary key, -- 유저 아이디
    username      varchar2(50) unique not null, -- 유저 닉네임
    password      varchar2(100) not null, -- 유저 비밀번호
    point         number not null
     */

    private String id;

    private String username;

    private String password;

    private int point;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        point = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
