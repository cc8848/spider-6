package controller;

import po.Area;
import service.AreaService;
import service.Impl.AreaserviceImpl;
import util.getUrl;
import util.urlQueue;

import java.util.Map;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class AreaController {
    private AreaService areaService = new AreaserviceImpl();
    private  final int   ID_LENGTH = 12;

    public void savaArea(urlQueue url,int arrIndex) {
        String[] className = {"provincetr","citytr","countytr","towntr","villagetr"};
        String areaId;
        String areaName;
        String temp;
        String nextUrl;
        urlQueue nextUrlQue = new urlQueue();
        String idPrefix;
        while (arrIndex < 5){
            int urlLen = url.getLength();
            for (int n = 0; n < urlLen; n ++) {
                String lastUrl = (String) url.pop();
                System.out.println("URL:"+lastUrl);
                Map<String,Object> map = getUrl.getUrlByClass(className[arrIndex],lastUrl);
                if (map.size() == 2) {
                    urlQueue name = (urlQueue) map.get("name");
                    urlQueue href = (urlQueue) map.get("href");
                    int len = name.getLength();
                    for (int m = 0; m < len; m ++) {
                        temp = (String)href.pop();
                        nextUrl = lastUrl.substring(0,lastUrl.lastIndexOf("/")) +"/"+ temp;
                        System.out.println("NEXT URL:"+nextUrl);
                        nextUrlQue.push(nextUrl);
                        idPrefix = temp.substring(temp.indexOf("/")+1,temp.lastIndexOf("."));
                        areaId = idPrefix + String.format("%1$0"+(ID_LENGTH - idPrefix.length())+"d",0);
                        areaName = (String)name.pop();
                        Area area = new Area();
                        area.setAreaId(areaId);
                        area.setAreaName(areaName);
                        areaService.insertArea(area);
                    }
                }else if (map.containsKey("all")){
                    urlQueue all = (urlQueue) map.get("all");
                    int len = all.getLength();
                    for (int m = 0; m < len; m ++) {
                        temp = (String) all.pop();
                        areaId = temp.substring(0,temp.indexOf(" "));
                        areaName = temp.substring(temp.lastIndexOf(" ") + 1);
                        System.out.println(areaId);
                        System.out.println(areaName);
                        Area area = new Area();
                        area.setAreaId(areaId);
                        area.setAreaName(areaName);
                        areaService.insertArea(area);
                    }
                }
            }
            if (!nextUrlQue.isEmpty()){
                System.out.println(1);
                arrIndex ++;
                savaArea(nextUrlQue,arrIndex);
            }
        }



    }

}
