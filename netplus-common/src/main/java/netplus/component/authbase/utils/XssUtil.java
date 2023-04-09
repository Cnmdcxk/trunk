package netplus.component.authbase.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

/**
 * xss非法标签过滤工具类
 * 过滤html中的xss字符
 */
public class XssUtil {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    private static final Whitelist whitelist = Whitelist.basicWithImages();

    /** 配置过滤化参数,不对代码进行格式化 */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        whitelist.addAttributes(":all", "style");
    }

    public static String cleanSql(String value) {
        return value.replaceAll(" 1=1 ", "")
                .replaceAll("<", "＜")
                .replaceAll(">", "＞")
                .replaceAll("\'", "‘")
                .replaceAll("\"", "“")
                .replaceAll("%", "％")
                .replaceAll(" and ", "")
                .replaceAll(" or ", "")
                .replaceAll("%22", "")
                .replaceAll("%27", "")
                .replaceAll("%3E", "")
                .replaceAll("%3e", "")
                .replaceAll("%3C", "")
                .replaceAll("%3c", "")
                .replaceAll(";", "")
                .replaceAll("|", "")
                .replaceAll("&", "")
                .replaceAll("$", "")
                .replaceAll("@", "＠")
                .replaceAll("\\'", "＼’")
                .replaceAll("\\\"", "＼“")
                .replaceAll("\\(", "（")
                .replaceAll("\\)", "）")
//                .replaceAll("/+", "＋")
//                .replaceAll("CR", "")
//                .replaceAll("LR", "")
                .replaceAll(",", "，");
    }

    public static String clean(String content) {
        if(StringUtils.isNotBlank(content)){
            //不删除前后的空格 content = content.trim();
            content = Jsoup.clean(content, "", whitelist, outputSettings);
            content = cleanSql(content);
            return content;
        }else{
            return content;
        }
    }

    public static void main(String[] args) throws IOException {
        String text = "";

        System.out.println("----------------------------");
        text = "@#$%^&*()<>:\'\" " +
                " and or 1=1 ";
        System.out.println("过滤前："+text);
        System.out.println("过滤后："+clean(text));

        System.out.println("----------------------------");
        text = null;
        System.out.println("过滤前："+text);
        System.out.println("过滤后："+clean(text));

        System.out.println("----------------------------");
        text = "   <a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss   ";
        System.out.println("过滤前："+text);
        System.out.println("过滤后："+clean(text));

        System.out.println("----------------------------");
        text = "      ";
        System.out.println("过滤前："+text);
        System.out.println("过滤后："+clean(text));


        System.out.println("----------------------------");
        text = "  hello!!!    ";
        System.out.println("过滤前："+text);
        System.out.println("过滤后："+clean(text));


    }
}
