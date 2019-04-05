package crawlbaike.examples;

import java.util.Map;

import crawlbaike.CrawlBaike;

/**
 * Created by 丛 on 2019/4/5 0005.
 */

public class example1 {

    public static void main(String... args) {
//        System.out.println(
//                CrawlBaike.getSummary("https://baike.sogou.com/v1850208.htm?fromTitle=%E6%B3%B0%E5%8B%92%C2%B7%E6%96%AF%E5%A8%81%E5%A4%AB%E7%89%B9")
//        );
        Map<String, String> map = CrawlBaike.getInfoItem("https://baike.sogou.com/v60559.htm?fromTitle=%E4%B8%83%E9%BE%99%E7%8F%A0");
        for (String key: map.keySet()) {
            System.out.println(key + ":" + map.get(key));
        }



//        String baiduBaikeOfTaylor =
//                "https://baike.baidu.com/item/%E6%B3%B0%E5%8B%92%C2%B7%E6%96%AF%E5%A8%81%E5%A4%AB%E7%89%B9/8472307?fr=aladdin";
//        System.out.println(CrawlBaike.getBaiduSummary(baiduBaikeOfTaylor));
//        System.out.println();
//        System.out.println(CrawlBaike.getBaiduInfoItem(baiduBaikeOfTaylor));
//
//        System.out.print("\n\n\n");
//
//        String sogouBaikeOfComicLongzhu =
//                "https://baike.sogou.com/v60559.htm?fromTitle=%E4%B8%83%E9%BE%99%E7%8F%A0";
//        System.out.println(CrawlBaike.getSogouSummary(sogouBaikeOfComicLongzhu));
//        System.out.println();
//        System.out.println(CrawlBaike.getSogouInfoItem(sogouBaikeOfComicLongzhu));


    }
}
