
import util.FileUtil;
import util.urlQueue;
import controller.AreaController;

import java.io.File;


/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class Main {


    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        AreaController areaController = new AreaController();
        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/index.html";
        urlQueue index = new urlQueue();
        index.push(url);
        areaController.savaArea(index,0);

        File file = new File("C:\\Users\\YangXuhui\\Desktop\\err.txt");
        File file1 = new File("C:\\Users\\YangXuhui\\Desktop\\err1.txt");
        //如果存在存取错误信息的文件则处理
        if (file.exists()) {
            try {
                String result = FileUtil.readTxtFile(file);
                String[] all = result.split("FGX");
                int len = all.length;
                String[] urls = new String[len];
                String[] classNames = new String[len];
                for (int n= 0; n < len; n ++) {
                    urls[n] = all[n].substring(0,all[n].indexOf(" "));
                    classNames[n] = all[n].substring(all[n].indexOf(" ")+1);
                    if (areaController.handdleErr(urls[n],classNames[n])) {
                        all[n] = null;
                    }
                    if (FileUtil.createFile(file1)) {
                        FileUtil.writeTxtFile(all[n],file1);
                    }
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }

        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)/60000+"分钟");
    }

}
