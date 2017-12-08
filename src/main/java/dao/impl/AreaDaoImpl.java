package dao.impl;

import dao.AreaDao;
import po.Area;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class AreaDaoImpl implements AreaDao {

    public boolean insertArea(Area area,Connection con,PreparedStatement pstmt){
        String sql = "insert into area(area_id, name) values(?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, area.getAreaId());
            pstmt.setString(2, area.getAreaName());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException var1) {
            var1.printStackTrace();
            return false;
        }
    }

}
