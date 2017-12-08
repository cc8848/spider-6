package dao;

import po.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public interface AreaDao {

    public boolean insertArea(Area area, Connection con, PreparedStatement pstmt);

}
