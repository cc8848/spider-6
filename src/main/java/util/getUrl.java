package util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 杨旭晖 on 2017/12/7.
 */
public class getUrl {

    /**
     * @param url
     * 待连接的地址
     * @param className
     * 需要匹配信息的标签class
     * @return 存储队列的map
     * */
    public static Map<String, Object> getUrlByClass(String className, String url) {
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
        Connection conn = Jsoup.connect(url);
        conn.header("user-agent", userAgent);
        Map<String, Object> map = new HashMap<String, Object>();
        urlQueue href = new urlQueue();
        urlQueue name = new urlQueue();
        urlQueue all = new urlQueue();
        urlQueue errUrl = new urlQueue();
        urlQueue errClassName = new urlQueue();
        Document doc = null;
        try {
            doc = conn.data("query", "Java")
                    .userAgent("Mozilla").cookie("auth", "token")
                    .timeout(3000).post();
        } catch (IOException e) {
            try {
                doc = Jsoup.connect(url).timeout(3000).get();
            }catch (IOException e1){
                if (url != null && className!= null){
                    errUrl.push(url);
                    errClassName.push(className);
                    map.put("errUrl",errUrl);
                    map.put("errClassName",errClassName);
                    System.out.println("error:"+url);
                    return map;
                }
            }
        }

        Elements targets = doc.select("." + className + " a");
        int len = targets.size();

        if (targets.size() != 0 ) {
            for (int i = 0; i < len; i++) {
                if ( i < len - 1 && targets.get(i).attr("href").equals(targets.get(i + 1).attr("href"))) {
                    continue;
                }
                name.push(targets.get(i).text());
                href.push(targets.get(i).attr("href"));
            }
            map.put("name", name);
            map.put("href", href);
            return map;
        } else {
            Elements td = doc.select("." + className);
            for (int i = 0; i < td.size(); i++) {
                all.push(td.get(i).text());
            }
            map.put("all", all);
            return map;
        }

    }

}
