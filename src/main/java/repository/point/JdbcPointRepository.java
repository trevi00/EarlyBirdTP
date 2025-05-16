package repository.point;

import database.DatabaseConnection;
import database.UserDTO;

import java.sql.Connection;

public class JdbcPointRepository{

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        DBpointselect psel = new DBpointselect();
        DBPointUpdate pupd = new DBPointUpdate();

        UserDTO u = psel.select("topblame", connection);  // ✅ 전달
        int point = 10 + u.getPoint();
        u.setPoint(point);

        pupd.update(u, connection);  // ✅ 전달

        try {
            if (connection != null) connection.close();  // 마지막에 닫기
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
