package controller;

import po.Area;
import service.AreaService;
import service.Impl.AreaserviceImpl;
import util.DBUtil;
import util.FileUtil;
import util.getUrl;
import util.urlQueue;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class AreaController {
    private AreaService areaService = new AreaserviceImpl();
    private final int ID_LENGTH = 12;
    private String[] className = {"provincetr", "citytr", "countytr", "towntr", "villagetr"};
    private boolean flag = false;

    public boolean savaArea(urlQueue url, int arrIndex) {
        String areaId;
        String areaName;
        String temp;
        String nextUrl;
        urlQueue nextUrlQue = new urlQueue();
        urlQueue errUrl = new urlQueue();
        urlQueue errClassName = new urlQueue();
        String idPrefix;
        Connection con = DBUtil.getCon();
        PreparedStatement pstmt = null;
        while (arrIndex < 5) {
            int urlLen = url.getLength();
            for (int n = 0; n < urlLen; n++) {
                String lastUrl = (String) url.pop();
                System.out.println("URL:" + lastUrl);
                Map<String, Object> map = getUrl.getUrlByClass(className[arrIndex], lastUrl);
                if (map.containsKey("name")) {
                    urlQueue name = (urlQueue) map.get("name");
                    urlQueue href = (urlQueue) map.get("href");
                    int len = name.getLength();
                    for (int m = 0; m < len; m++) {
                        temp = (String) href.pop();
                        nextUrl = lastUrl.substring(0, lastUrl.lastIndexOf("/")) + "/" + temp;
                        System.out.println("NEXT URL:" + nextUrl);
                        nextUrlQue.push(nextUrl);
                        idPrefix = temp.substring(temp.indexOf("/") + 1, temp.lastIndexOf("."));
                        areaId = idPrefix + String.format("%1$0" + (ID_LENGTH - idPrefix.length()) + "d", 0);
                        areaName = (String) name.pop();
                        Area area = new Area();
                        area.setAreaId(areaId);
                        area.setAreaName(areaName);
                        areaService.insertArea(area, con, pstmt);
                    }
                    flag = true;
                } else if (map.containsKey("all")) {
                    urlQueue all = (urlQueue) map.get("all");
                    int len = all.getLength();
                    for (int m = 0; m < len; m++) {
                        temp = (String) all.pop();
                        areaId = temp.substring(0, temp.indexOf(" "));
                        areaName = temp.substring(temp.lastIndexOf(" ") + 1);
                        System.out.println(areaId);
                        System.out.println(areaName);
                        Area area = new Area();
                        area.setAreaId(areaId);
                        area.setAreaName(areaName);
                        areaService.insertArea(area, con, pstmt);
                    }
                    flag = true;
                } else if (map.containsKey("errUrl")) {                             //存储访问出错的url和类名称
                    urlQueue e1 = (urlQueue) map.get("errUrl");
                    urlQueue e2 = (urlQueue) map.get("errClassName");
                    int len = e1.getLength();
                    for (int ee = 0; ee < len; ee++) {
                        errUrl.push(e1.pop());
                        errClassName.push(e2.pop());
                    }
                }
                flag = false;
            }
            if (!nextUrlQue.isEmpty()) {
                arrIndex++;
                savaArea(nextUrlQue, arrIndex);
            } else {
                break;
            }
        }

        DBUtil.allClose(con, pstmt, null);

        /**
         * 把爬取失败的url存入txt待处理
         * */
        if (!errClassName.isEmpty()) {
            try {
                File file = new File("C:\\Users\\YangXuhui\\Desktop\\err.txt");
                if (FileUtil.createFile(file)) {
                    for (int n = 0, len = errClassName.getLength(); n < len; n++) {
                        String allErr = "";
                        String errMsg = errUrl.pop() + " " + errClassName.pop() + "FGX";
                        allErr += errMsg;
                        if (n == len - 1) {
                            FileUtil.writeTxtFile(allErr,file);
                        }
                    }

                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            flag = false;
        }
        return flag;
    }

    public boolean handdleErr(String url, String handdleClassName) {
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------处理访问失败URL----------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        try {
            Thread thread = Thread.currentThread();
            thread.sleep(3000);//暂停1.5秒后程序继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int times = 10;
        urlQueue nextUrlQue = new urlQueue();
        urlQueue errUrl = new urlQueue();
        urlQueue errClassName = new urlQueue();
        urlQueue nextHanddleUrlQue = new urlQueue();
        String idPrefix;
        String nextUrl;
        String areaId;
        String areaName;
        String temp;
        String nextClassName;
        Connection con = DBUtil.getCon();
        PreparedStatement pstmt = null;
        Map<String, Object> map = getUrl.getUrlByClass(url, handdleClassName);
        if (map.containsKey("name")) {
            urlQueue name = (urlQueue) map.get("name");
            urlQueue href = (urlQueue) map.get("href");
            for (int n = 0, len = name.getLength(); n < len; n++) {
                temp = (String) href.pop();
                nextUrl = url.substring(0, url.lastIndexOf("/")) + "/" + temp;
                System.out.println("NEXT URL:" + nextUrl);
                nextUrlQue.push(nextUrl);
                idPrefix = temp.substring(temp.indexOf("/") + 1, temp.lastIndexOf("."));
                areaId = idPrefix + String.format("%1$0" + (ID_LENGTH - idPrefix.length()) + "d", 0);
                areaName = (String) name.pop();
                Area area = new Area();
                area.setAreaId(areaId);
                area.setAreaName(areaName);
                areaService.insertArea(area, con, pstmt);
                nextClassName = handdleClassName;
                if (!nextUrlQue.isEmpty()) {
                    int index = Arrays.binarySearch(className, nextClassName);
                    flag = savaArea(nextUrlQue, index);
                }else {
                    flag = true;
                }
            }
        } else if (map.containsKey("all")) {
            urlQueue all = (urlQueue) map.get("all");
            for (int n = 0, len = all.getLength(); n < len; n++) {
                temp = (String) all.pop();
                areaId = temp.substring(0, temp.indexOf(" "));
                areaName = temp.substring(temp.lastIndexOf(" ") + 1);
                System.out.println(areaId);
                System.out.println(areaName);
                Area area = new Area();
                area.setAreaId(areaId);
                area.setAreaName(areaName);
                areaService.insertArea(area, con, pstmt);
                flag = true;
            }
        } else if (map.containsKey("errUrl")) {
            flag = false;
        }
        DBUtil.allClose(con, pstmt, null);

        return flag;
    }

}
