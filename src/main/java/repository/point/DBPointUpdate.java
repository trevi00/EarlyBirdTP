package repository.point;

import database.DatabaseConnection;
import database.UserDTO;
import prototype.CheckUser.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBPointUpdate {
    private PreparedStatement pstmt = null;
    private ResultSet rs;
    public int update(UserDTO updateDto, Connection con){
        int rows = 0;

        try {
            String sql = "UPDATE POINTS SET TOTAL = ? WHERE USER_ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, updateDto.getPoint());
            pstmt.setString(2, updateDto.getID());

            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBClose.close(null, pstmt, rs); // con은 main에서 관리
        }

        return rows;
    }
}
