package crawlbaike;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 丛 on 2019/1/9 0009.
 */

public class CrawlBaike {

    public static String getSummary(String url) {
        String domain = getUrlMainDomain(url, false);
        if (domain.contains("baidu")) {
            return getBaiduSummary(url);
        } else if (domain.contains("sogou")) {
            return getSogouSummary(url);
        } else
            return "";
    }

    public static Map<String, String> getInfoItem(String url) {
        String domain = getUrlMainDomain(url, false);
        if (domain.contains("baidu")) {
            return getBaiduInfoItem(url);
        } else if (domain.contains("sogou")) {
            return getSogouInfoItem(url);
        } else
            return Collections.emptyMap();
    }

    public static String getBaiduSummary(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            StringBuilder sb = new StringBuilder("");
            Elements es;
            es = doc.getElementsByClass("lemma-summary")
                    .select("div[class=para]");
            if (es != null) {
                for (Element e: es) {
                    sb.append(e.text().replaceAll("\\s?\\[.+?]\\s?", ""));
                    if (es.indexOf(e) != es.size() - 1) {
                        sb.append("\n");
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Map<String, String> getBaiduInfoItem(String url) {
        try {
            String domain = getUrlMainDomain(url, false);
            url = url.replace("wapbaike", "baike");
            Document doc = Jsoup.connect(url).get();
            Map<String, String> map = new LinkedHashMap<>();

            if (domain.contains("baidu")) {
                Elements esInfo = doc.getElementsByClass("basic-info cmn-clearfix");
                Elements esLeft = esInfo.select("dl[class=basicInfo-block basicInfo-left]");
                esLeft.select("div[class=basicInfo-overlap]").remove();
                Elements esLeftName = esLeft.select("dt[class=basicInfo-item name]");
                Elements esLeftValue = esLeft.select("dd[class=basicInfo-item value]");
                Elements esRight = esInfo.select("dl[class=basicInfo-block basicInfo-right]");
                esRight.select("div[class=basicInfo-overlap]").remove(); // 删除展开更多的内容
                Elements esRightName = esRight.select("dt[class=basicInfo-item name]");
                Elements esRightValue = esRight.select("dd[class=basicInfo-item value]");
                esRightValue.removeClass("basicInfo-overlap");
                for (int i = 0; i < esLeftName.size(); i++) {
                    map.put(esLeftName.get(i).text().replaceAll("\\[.+?]", ""),
                            esLeftValue.get(i).text().replaceAll("\\[.+?]", ""));
                }
                for (int i = 0; i < esRightName.size(); i++) {
                    map.put(esRightName.get(i).text().replaceAll("\\[.+?]", ""),
                            esRightValue.get(i).text()
                                    .replaceAll("\\[.+?]", "")
                                    .replace("展开", "等"));
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static String getSogouSummary(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements es = doc.getElementsByClass("abstract").select("p");
            StringBuilder sb = new StringBuilder();
            if (es != null) {
                for (Element e: es) {
                    sb.append(e.text().replaceAll("\\s?\\[.+?]\\s?", ""));
                    if (es.indexOf(e) != es.size() - 1) {
                        sb.append("\n");
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Map<String, String> getSogouInfoItem(String url) {
        try {
            LinkedHashMap<String, String> map = null;
            Document doc = Jsoup.connect(url).get();
            Elements es = doc.getElementsByClass("abstract_tbl");
            String[] strArr = es.text().replaceAll("\\s?\\[.+?]\\s?", "").split("\\s{3,30}");
            if (strArr.length > 0) {
                map = new LinkedHashMap<>();
                for (int i = 0; i < strArr.length; i += 2) {
                    if (i + 1 == strArr.length)
                        break;
                    map.put(strArr[i], strArr[i + 1]);
                }
            }
            if (map == null)
                return Collections.emptyMap();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private static Pattern patternDomain;

    /**
     * 获取url主域名
     * 如https://baike.baidu.com/item/%E6%B3%B0%E5%8B%92%C2%B7%E6%96%AF%E5%A8%81%E5%A4%AB%E7%89%B9/8472307?fr=aladdin
     * withScheme(true) return https://baike.baidu.com
     * withScheme(false) return baike.baidu.com
     * @param url
     * @param withScheme
     * @return
     */
    public static String getUrlMainDomain(String url, boolean withScheme) {
        if (patternDomain == null)
            patternDomain = Pattern.compile("(http|https)://(www.)?(\\w+(\\.)?)+");
        Matcher matcher = patternDomain.matcher(url);
        if (matcher.find()) {
            String result = matcher.group();
            if (withScheme) {
                return result;
            } else {
                return result.replace("http://", "")
                        .replace("https://", "");
            }
        }
        return "";
    }

}
