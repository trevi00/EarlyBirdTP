package team_prototype.database;

public class DatabaseConfig {

    private final static String IP = "@localhost";

    private final static String PORT = "1521";

    private final static String SID = "xe";

    final static String URL = "jdbc:oracle:thin:" +  IP + ":" + PORT + ":" + SID;

    final static String USER = "earlybird"; // 사용자 - DB

    final static String PASSWORD = "12345"; // 비밀번호

}
