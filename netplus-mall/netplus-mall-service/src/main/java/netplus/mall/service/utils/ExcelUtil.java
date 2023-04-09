package netplus.mall.service.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Excel 处理类
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static ExcelUtil build() {
        return new ExcelUtil();
    }

    /**
     * 输出名称
     */
    private String name;

    /**
     * 输出流
     */
    private HttpServletResponse response;

    /**
     * sheet name
     */
    private String sheet;

    /**
     * 列名配置
     */
    private Class<?> head;

    private Function<Integer, List<?>> data;

    public String getName() {
        return (name == null || name.trim().length() == 0) ? "" + System.currentTimeMillis() : name;
    }

    public ExcelUtil setName(String name) {
        this.name = name;
        return this;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ExcelUtil setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public String getSheet() {
        return (sheet == null || sheet.trim().length() == 0) ? "sheet" : sheet;
    }

    public ExcelUtil setSheet(String sheet) {
        this.sheet = sheet;
        return this;
    }

    public Class<?> getHead() {
        return head;
    }

    public ExcelUtil setHead(Class<?> head) {
        this.head = head;
        return this;
    }

    public Function<Integer, List<?>> getData() {
        return data;
    }

    public ExcelUtil setData(Function<Integer, List<?>> data) {
        this.data = data;
        return this;
    }

    public void write() throws Exception {

        validParam();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(this.getName(), "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), head).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet(getSheet()).build();
            // 循环加载数据，直到返回数据为空
            logger.info("循环加载数据，直到返回数据为空");
            loadData(list -> excelWriter.write(list, writeSheet));
        }

    }

    // 验证参数
    private void validParam() throws Exception {
        if (response == null) {
            throw new RuntimeException("输入流必须填写");
        }
        if (head == null) {
            throw new RuntimeException("输入标题不能为空");
        }
        if (data == null) {
            throw new RuntimeException("加载数据方法不能为空");
        }
    }

    // 循环读取数据
    private void loadData(Consumer<List<?>> consumer){
        Integer page = 0;
        boolean hasNext = true;
        do {
            page += 1;
            List<?> list = data.apply(page);
            if (list == null || list.isEmpty()) {
                hasNext = false;
                break;
            }
            // 加个限制，防止程序写的不好，无限循环
            if (page > 100000) {
                hasNext = false;
                break;
            }

            logger.info("list {}", new Gson().toJson(list));
            consumer.accept(list);
        } while (hasNext);
    }

}
