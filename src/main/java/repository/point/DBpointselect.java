package repository.point;

import database.DatabaseConnection;
import database.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBpointselect {
    private PreparedStatement pstmt = null;
    private ResultSet rs;
    Connection con = DatabaseConnection.getConnection();
    public UserDTO select(String ID, Connection con) {
        UserDTO userDto = null;
        try {
            String sql = "SELECT user_id, total FROM points WHERE user_id = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, ID);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                userDto = new UserDTO();
                userDto.setID(rs.getString("user_id"));
                userDto.setPoint(rs.getInt("total"));
            } else {
                System.out.println("❌ 해당 유저를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBClose.close(null, pstmt, rs); // con은 main에서 관리
        }
        return userDto;
    }
}
