package service.Impl;

import dao.AreaDao;
import dao.impl.AreaDaoImpl;
import po.Area;
import service.AreaService;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class AreaserviceImpl implements AreaService {
    private AreaDao areadao = new AreaDaoImpl();

    public boolean insertArea(Area area) {
        return areadao.insertArea(area);
    }
}
