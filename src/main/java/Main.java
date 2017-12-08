
import util.urlQueue;
import controller.AreaController;


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
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)/60000+"分钟");
    }

}
