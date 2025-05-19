package team_prototype.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sign {
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;
    public int insert(ConnectDB connectDB, UserDTO updateDTO) {
        int rows = 0;

        try {
            con = connectDB.getConnection();

            String sql = "INSERT INTO Users(ID, USERNAME, PASSWORD, DISPLAY_NAME) ";
            sql += "VALUES (?,?,?,?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, updateDTO.getUserid());
            pstmt.setString(2, updateDTO.getName());
            pstmt.setString(3, updateDTO.getPassword());
            pstmt.setString(4, updateDTO.getDisplayname());

            rows = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectDB.close(con, pstmt, rs);
        }
        return rows;
    }
}
