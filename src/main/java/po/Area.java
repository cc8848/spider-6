package po;

import java.io.Serializable;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class Area implements Serializable{
    private static final long serialVersionUID = -8625212957261853343L;

    private String areaId;
    private String areaName;

    public Area() {
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
