package service.Impl;

import dao.AreaDao;
import dao.impl.AreaDaoImpl;
import po.Area;
import service.AreaService;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class AreaserviceImpl implements AreaService {
    private AreaDao areadao = new AreaDaoImpl();

    public boolean insertArea(Area area, Connection con, PreparedStatement pstmt) {
        return areadao.insertArea(area,con,pstmt);
    }
}
