package netplus.excel.service.biz;

import netplus.component.entity.exceptions.NetPlusException;
import netplus.excel.api.pojo.ExcelTempData;
import netplus.excel.api.pojo.ExcelTempMemo;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.UploadExcelRequest;
import netplus.excel.api.vo.UploadResultVo;
import netplus.excel.service.analysis.ParseExcel;
import netplus.excel.service.dao.ExcelTempDataMapper;
import netplus.excel.service.dao.ExcelTempMemoMapper;
import netplus.utils.pager.Pager;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lhp on 2017/5/18.
 */
@Service
public class UploadExcelBiz {

    protected static final Log logger = LogFactory.getLog(UploadExcelBiz.class);

    private static final int downloadTimeout = 60 * 1000; // 60s, 1分种

    @Autowired
    private ExcelTempDataMapper excelTempDataDao;

    @Autowired
    private ExcelTempMemoMapper excelTempMemoDao;

    public UploadResultVo save(String appId, ExcelRequest request) {

        ParseExcel parseExcel = new ParseExcel();

        logger.info(String.format("准备解析：%s", request.getFilePath()));

        InputStream fileStream = null;

        try {
            fileStream = downloadFile(request.getFilePath());
        }
        catch (Exception e) {
            logger.error(String.format("解析文件：%s 时出错！", request.getFilePath()), e);
            e.printStackTrace();
        }

        if (fileStream == null) {
            throw new NetPlusException(String.format("文件流为空: %s", request.getFilePath()));
        }
        else {

            try {
                if (fileStream.available() <= 0)
                    throw new NetPlusException(String.format("文件流size为0: %s", request.getFilePath()));

            } catch (IOException ix) {
                throw new NetPlusException(ix.getMessage());
            }
        }

        parseExcel.setAppId(appId);
        parseExcel.setBatchCode(request.getBatchCode());
        parseExcel.setStartRowNo(request.getStartRowNum() == null ? 0 : request.getStartRowNum());
        parseExcel.setVersion(request.getVersion());
        parseExcel.setInputStream(fileStream);
        parseExcel.parse();

        //
        UploadExcelRequest request1 = new UploadExcelRequest();
        request1.setHead(saveHead(parseExcel.getHead(), parseExcel.getBatchCode(), request.getUsername()));
        request1.setBody(saveBody(parseExcel.getBody(), parseExcel.getAppId(), parseExcel.getBatchCode(), request.getUsername()));


        int rows = 0;
        Pager<ExcelTempData> pager = new Pager(request1.getBody(), 100);

        for (int i=1; i <= pager.getTotalPage(); i++) {
            rows = rows + excelTempDataDao.bulkInsert(pager.getPageData(i));
        }

        Pager<ExcelTempMemo> pager2 = new Pager(request1.getHead(), 100);
        for (int i=1; i <= pager2.getTotalPage(); i++) {
            excelTempMemoDao.bulkInsert(pager2.getPageData(i));
        }


//        int rows = excelTempDataDao.bulkInsert(request1.getBody());
//        excelTempMemoDao.bulkInsert(request1.getHead());

        UploadResultVo uploadResultVo = new UploadResultVo();
        uploadResultVo.setBatchCode(parseExcel.getBatchCode());
        uploadResultVo.setTotal(rows);

        return uploadResultVo;
    }

    private List<ExcelTempMemo> saveHead(Map<String, String> head, String batchCode, String username) {

        List<ExcelTempMemo> list = new ArrayList<>();

        Date now = new Date();

        for (Map.Entry<String, String> entry : head.entrySet()) {
            ExcelTempMemo entity = new ExcelTempMemo();
            entity.setBatchCode(batchCode);
            entity.setField(entry.getKey());
            entity.setValue(entry.getValue());
            entity.setCreatePerson(username);
            entity.setCreateDate(now);
            list.add(entity);
        }

        return list;

    }

    private List<ExcelTempData> saveBody(List<Map<String, String>> body, String appId, String batchCode, String username) {

        List<ExcelTempData> list = new ArrayList<>();

        for (Map<String, String> item : body) {

            ExcelTempData entity = new ExcelTempData();
            entity.setAppId(appId);
            entity.setCreatePerson(username);
            entity.setBatchCode(batchCode);
            entity.setField1(item.getOrDefault("field1", ""));
            entity.setField2(item.getOrDefault("field2", ""));
            entity.setField3(item.getOrDefault("field3", ""));
            entity.setField4(item.getOrDefault("field4", ""));
            entity.setField5(item.getOrDefault("field5", ""));
            entity.setField6(item.getOrDefault("field6", ""));
            entity.setField7(item.getOrDefault("field7", ""));
            entity.setField8(item.getOrDefault("field8", ""));
            entity.setField9(item.getOrDefault("field9", ""));
            entity.setField10(item.getOrDefault("field10", ""));
            entity.setField11(item.getOrDefault("field11", ""));
            entity.setField12(item.getOrDefault("field12", ""));
            entity.setField13(item.getOrDefault("field13", ""));
            entity.setField14(item.getOrDefault("field14", ""));
            entity.setField15(item.getOrDefault("field15", ""));
            entity.setField16(item.getOrDefault("field16", ""));
            entity.setField17(item.getOrDefault("field17", ""));
            entity.setField18(item.getOrDefault("field18", ""));
            entity.setField19(item.getOrDefault("field19", ""));
            entity.setField20(item.getOrDefault("field20", ""));
            entity.setField21(item.getOrDefault("field21", ""));
            entity.setField22(item.getOrDefault("field22", ""));

            entity.setField23(item.getOrDefault("field23", ""));
            entity.setField24(item.getOrDefault("field24", ""));
            entity.setField25(item.getOrDefault("field25", ""));
            entity.setField26(item.getOrDefault("field26", ""));
            entity.setField27(item.getOrDefault("field27", ""));
            entity.setField28(item.getOrDefault("field28", ""));
            entity.setField29(item.getOrDefault("field29", ""));
            entity.setField30(item.getOrDefault("field30", ""));
            entity.setField31(item.getOrDefault("field31", ""));
            entity.setField32(item.getOrDefault("field32", ""));
            entity.setField33(item.getOrDefault("field33", ""));
            entity.setField34(item.getOrDefault("field34", ""));
            entity.setField35(item.getOrDefault("field35", ""));
            entity.setField36(item.getOrDefault("field36", ""));
            entity.setField37(item.getOrDefault("field37", ""));
            entity.setField38(item.getOrDefault("field38", ""));
            entity.setField39(item.getOrDefault("field39", ""));
            entity.setField40(item.getOrDefault("field40", ""));
            entity.setField41(item.getOrDefault("field41", ""));
            entity.setField42(item.getOrDefault("field42", ""));
            entity.setField43(item.getOrDefault("field43", ""));
            entity.setField44(item.getOrDefault("field44", ""));
            entity.setField45(item.getOrDefault("field45", ""));
            entity.setField46(item.getOrDefault("field46", ""));
            entity.setField47(item.getOrDefault("field47", ""));
            entity.setField48(item.getOrDefault("field48", ""));
            entity.setField49(item.getOrDefault("field49", ""));
            entity.setField50(item.getOrDefault("field50", ""));
            entity.setField51(item.getOrDefault("field51", ""));
            entity.setField52(item.getOrDefault("field52", ""));
            entity.setField53(item.getOrDefault("field53", ""));
            entity.setField54(item.getOrDefault("field54", ""));
            entity.setField55(item.getOrDefault("field55", ""));
            entity.setField56(item.getOrDefault("field56", ""));
            entity.setField57(item.getOrDefault("field57", ""));
            entity.setField58(item.getOrDefault("field58", ""));
            entity.setField59(item.getOrDefault("field59", ""));
            entity.setField60(item.getOrDefault("field60", ""));
            entity.setField61(item.getOrDefault("field61", ""));
            entity.setField62(item.getOrDefault("field62", ""));
            entity.setField63(item.getOrDefault("field63", ""));
            entity.setField64(item.getOrDefault("field64", ""));
            entity.setField65(item.getOrDefault("field65", ""));
            entity.setField66(item.getOrDefault("field66", ""));
            entity.setField67(item.getOrDefault("field67", ""));
            entity.setField68(item.getOrDefault("field68", ""));
            entity.setField69(item.getOrDefault("field69", ""));
            entity.setField70(item.getOrDefault("field70", ""));
            entity.setField71(item.getOrDefault("field71", ""));
            entity.setField72(item.getOrDefault("field72", ""));
            entity.setField73(item.getOrDefault("field73", ""));
            entity.setField74(item.getOrDefault("field74", ""));
            entity.setField75(item.getOrDefault("field75", ""));
            entity.setField76(item.getOrDefault("field76", ""));
            entity.setField77(item.getOrDefault("field77", ""));
            entity.setField78(item.getOrDefault("field78", ""));
            entity.setField79(item.getOrDefault("field79", ""));
            entity.setField80(item.getOrDefault("field80", ""));
            list.add(entity);
        }

        return list;

    }

    private InputStream downloadFile(String src_file) throws Exception {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(src_file);
            httpget.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(downloadTimeout)
                    .setConnectTimeout(downloadTimeout)
                    .setSocketTimeout(downloadTimeout)
                    .build());

            CloseableHttpResponse response = httpclient.execute(httpget);
            org.apache.http.HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            // 复制流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            //
            is.close();

            return new ByteArrayInputStream(baos.toByteArray());
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}
