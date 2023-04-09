package netplus.utils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by SR on 2016/12/7.
 */
public class PinyinTool {
    HanyuPinyinOutputFormat format = null;

    public static enum Type {
        UPPERCASE,              //全部大写
        LOWERCASE,              //全部小写
        FIRSTUPPER              //首字母大写
    }

    public PinyinTool() {
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    public String toPinYin(String str, Type lowercase) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.UPPERCASE);
    }

    public String toPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, spera, Type.UPPERCASE);
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     * 如： 明天 转换成 MINGTIAN
     *
     * @param str
     * @param spera
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
        if (str == null || str.trim().length() == 0)
            return "";
        if (type == Type.UPPERCASE)
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        else
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        String py = "";
        String temp = "";
        String[] t;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c <= 128)
                py += c;
            else {
                t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (t == null)
                    py += c;
                else {
                    temp = t[0];
                    if (type == Type.FIRSTUPPER)
                        temp = t[0].toUpperCase().charAt(0) + temp.substring(1);
                    py += temp + (i == str.length() - 1 ? "" : spera);
                }
            }
        }
        return py.trim();
    }

    /**
     *
     */
    /**
     * 把一个列表数据
     */
    public static <T, R> List<R> doPinyin(List<T> data, Function<T, String> getNameFn, BiFunction<T, String, R> resultFn) {
        if (ObjectUtil.nonEmpty(data)) {
            PinyinTool pinyinTool = new PinyinTool();

            return data
                    .stream()
                    .map(d -> {
                        try {
                            String result = pinyinTool.toPinYin(getNameFn.apply(d), "").toUpperCase().substring(0, 1);
                            return resultFn.apply(d, result);
                        } catch (BadHanyuPinyinOutputFormatCombination e) {
                            return resultFn.apply(d, "R");
                        }
                    }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static String getUpperStr(String name) {
        if (StringUtils.isNotBlank(name)) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                String characters = String.valueOf(name.charAt(i));
                str.append(converterToFirstSpell(characters).toUpperCase().charAt(0));
            }
            return str.toString();
        }
        return "";
    }

    public static String converterToFirstSpell(String str) {
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = str.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char characters : nameChar) {
            String string = String.valueOf(characters);
            if (string.matches("[\\u4e00-\\u9fa5]")) {
                try {
                    String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(characters, defaultFormat);
                    pinyinName.append(mPinyinArray[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(characters);
            }
        }
        return pinyinName.toString();
    }


    public static void main(String[] args) throws Exception {
        PinyinTool tool = new PinyinTool();
        System.out.println(getUpperStr("厦门"));
        System.out.println(tool.toPinYin("夏季", ""));
    }
}