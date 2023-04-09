package netplus.excel.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.excel.api.pojo.ExportTemplate;
import netplus.excel.api.request.ExportExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.service.analysis.ExcelUtil;
import netplus.excel.service.dao.ExportTemplateMapper;
import netplus.upload.api.rest.FileUploadRest;
import netplus.upload.api.vo.UploadResult;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportBiz {

    private static Logger logger = LoggerFactory.getLogger(ExportBiz.class);

    @Autowired
    private ExportTemplateMapper exportTemplateMapper;

    @Autowired
    private FileUploadRest fileUploadRest;

    public final static String remark = "表格填写注意事项：\n" +
            "1.使用Excel模板报价，必须使用下载的该模板进行填写，不能新增或者删减行或者列数据\n" +
            "2.表格中标题栏有注明为“必填”的为必填数据，注明为“选填”的为选填数据，未标明“必填”或者“选填”的为不需要填写的数据。\n" +
            "3.可填写的数据中，如有要求填写格式的，必须参照提供的格式要求进行填写，如自报供货日期，必须按照格式2020/12/06进行填写。\n" +
            "4.有自报供货日期要求的，供货日期必须晚于报价日期（不含当天）";

    public <T> String genExcel(String appId, GenExcelRequest<T> genExcelRequest) throws IOException {

        if (StringUtils.isEmpty(genExcelRequest.getTemplateName()))
            throw new NetPlusException("模板不能为空");

        if (StringUtils.isEmpty(genExcelRequest.getSheetName()))
            throw new NetPlusException("文件名不能为空");

        logger.info(String.format("genExcel: 共 %s 条 - %s", genExcelRequest.getItems().size(), genExcelRequest.getTemplateName()));

        if (genExcelRequest.getItems().size() > 5000){
            throw new NetPlusException("导出数据超过5000条，请过滤后重试。");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("templateName", genExcelRequest.getTemplateName());
        List<ExportTemplate> exportTemplates = exportTemplateMapper.selectByTemplateName(map);

        if (exportTemplates.size() == 0)
            throw new NetPlusException("模板不存在");

        // rows
        List<Map<String, Object>> rows = new ArrayList<>();
        for (T i : genExcelRequest.getItems()) {
            Map<String, Object> source = ObjectUtil.transBeanToMap(i);

            // 数据库的结果已经排序
            Map<String, Object> target = new HashMap<>();
            for (ExportTemplate template : exportTemplates) {
                String fieldName = template.getFieldName();
                String title = template.getTitle();
                if (source.containsKey(fieldName)) {
                    target.put(title, source.get(fieldName));
                } else {
                    target.put(title, "");
                }
            }
            rows.add(target);
        }

        // titles
        List<String> titles = new ArrayList<>();
        for (ExportTemplate template : exportTemplates) {
            String title = template.getTitle();
            titles.add(title);
        }

        logger.info("genExcel: 数据组合结束");

        // 释放些对象：
        genExcelRequest.getItems().clear();
        // ==

        Workbook workbook = ExcelUtil.exportToWorkBook(
                null,
                rows,
                titles.toArray(new String[exportTemplates.size()]),
                genExcelRequest.getSheetName(),
                ExcelUtil.ExcelFileType.XLSX);

        logger.info("genExcel: 生成excel");

        // 为第一个 sheet 插入一行备注
        if ("Y".equals(genExcelRequest.getHasRemark())) {
            workbook = ExcelUtil.addFirstRowRemark(workbook, remark);
        }


        // 上传并生成文件
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            workbook.write(out);

            // 释放对象
            workbook.close();
            titles.clear();
            rows.clear();

            //
            in = new ByteArrayInputStream(out.toByteArray());
            MultipartFile file = new MockMultipartFile(
                    "file",
                    String.format("%s.xlsx", genExcelRequest.getSheetName()),
                    null,
                    in);

            logger.info("genExcel: MockMultipartFile完成");

            UploadResult uploadResult = fileUploadRest.doUpload4Download(file);

            logger.info(String.format("genExcel: upload result: %s", new Gson().toJson(uploadResult)));

            return uploadResult.getUrl();

        } finally {
            if (out != null)
                out.close();

            if (in != null)
                in.close();
        }
    }


    public <T> String genExcelWithHead(String appId, GenExcelRequest<T> genExcelRequest, String head) throws IOException {
        if (StringUtils.isEmpty(genExcelRequest.getTemplateName()))
            throw new NetPlusException("模板不能为空");

        if (StringUtils.isEmpty(genExcelRequest.getSheetName()))
            throw new NetPlusException("文件名不能为空");

        Map<String, Object> map = new HashMap<>();
        map.put("appId", appId);
        map.put("templateName", genExcelRequest.getTemplateName());
        List<ExportTemplate> exportTemplates = exportTemplateMapper.selectByTemplateName(map);

        if (exportTemplates.size() == 0)
            throw new NetPlusException("模板不存在");

        logger.info(String.format("genExcel: 共 %s 条", genExcelRequest.getItems().size()));

        // rows
        List<Map<String, Object>> rows = new ArrayList<>();
        for (T i : genExcelRequest.getItems()) {
            Map<String, Object> source = ObjectUtil.transBeanToMap(i);

            // 数据库的结果已经排序
            Map<String, Object> target = new HashMap<>();
            for (ExportTemplate template : exportTemplates) {
                String fieldName = template.getFieldName();
                String title = template.getTitle();
                if (source.containsKey(fieldName)) {
                    target.put(title, source.get(fieldName));
                } else {
                    target.put(title, "");
                }
            }
            rows.add(target);
        }

        // titles
        List<String> titles = new ArrayList<>();
        for (ExportTemplate template : exportTemplates) {
            String title = template.getTitle();
            titles.add(title);
        }

        logger.info("genExcel: 数据组合结束");

        // 释放些对象：
        genExcelRequest.getItems().clear();
        // ==

        Workbook workbook = ExcelUtil.exportToWorkBoolWithHead(
                null,
                rows,
                head,
                titles.toArray(new String[exportTemplates.size()]),
                genExcelRequest.getSheetName(),
                ExcelUtil.ExcelFileType.XLSX);

        logger.info("genExcel: 生成excel");

        // 上传并生成文件
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            workbook.write(out);

            // 释放对象
            workbook.close();
            titles.clear();
            rows.clear();

            //
            in = new ByteArrayInputStream(out.toByteArray());
            MultipartFile file = new MockMultipartFile(
                    "file",
                    String.format("%s.xlsx", genExcelRequest.getSheetName()),
                    null,
                    in);

            logger.info("genExcel: MockMultipartFile完成");

            UploadResult uploadResult = fileUploadRest.doUpload(file);

            logger.info(String.format("genExcel: upload result: %s", new Gson().toJson(uploadResult)));

            return uploadResult.getUrl();

        } finally {
            if (out != null)
                out.close();

            if (in != null)
                in.close();
        }
    }

    /**
     * 传入标题转换excel
     *
     * @param appId
     * @param request
     * @return
     * @throws IOException
     */
    public <T> String genExcelUrl(String appId, ExportExcelRequest<T> request) throws IOException {
        if (ObjectUtil.isEmpty(request.getTitles())) {
            throw new NetPlusException("标题不能为空");
        }

        if (StringUtils.isEmpty(request.getSheetName()))
            throw new NetPlusException("文件名不能为空");
        // rows
        List<Map<String, Object>> rows = new ArrayList<>();
        for (T i : request.getItems()) {
            Map<String, Object> source = ObjectUtil.transBeanToMap(i);

            // 数据库的结果已经排序
            Map<String, Object> target = new HashMap<>();
            for (String key : request.getTitles().keySet()) {
                String fieldName = request.getTitles().get(key);
                String title = key;
                if (source.containsKey(fieldName)) {
                    target.put(title, source.get(fieldName));
                } else {
                    target.put(title, "");
                }
            }
            rows.add(target);
        }

        // titles
        List<String> titles = new ArrayList<String>(request.getTitles().keySet());

        logger.info("genExcel: 数据组合结束");

//        logger.info(String.format("title: %s", new Gson().toJson(titles)));
//        logger.info(String.format("rows: %s", new Gson().toJson(rows)));

        Workbook workbook = ExcelUtil.exportToWorkBook(
                null,
                rows,
                titles.toArray(new String[titles.size()]),
                request.getSheetName(),
                ExcelUtil.ExcelFileType.XLSX);

        logger.info("genExcel: 生成excel");

        // 上传并生成文件
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            workbook.write(out);

            in = new ByteArrayInputStream(out.toByteArray());
            MultipartFile file = new MockMultipartFile(
                    "file",
                    String.format("%s.xlsx", request.getSheetName()),
                    null,
                    in);

            UploadResult uploadResult = fileUploadRest.doUpload(file);

            logger.info(String.format("genExcel: upload result: %s", new Gson().toJson(uploadResult)));

            return uploadResult.getUrl();

        } finally {
            if (out != null)
                out.close();

            if (in != null)
                in.close();
        }
    }
}
