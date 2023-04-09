package netplus.excel.service.biz;

import java.util.List;

import netplus.excel.api.pojo.ExcelTempData;
import netplus.excel.api.pojo.ExcelTempMemo;
import netplus.excel.service.dao.ExcelTempDataMapper;

import netplus.excel.service.dao.ExcelTempMemoMapper;
import org.springframework.util.ObjectUtils;

/**
 * Created by lhp on 2017/5/3.
 * 查询excel数据
 */
public class ExcelDataBiz {
    private ExcelTempDataMapper excelTempDataDao;
    private ExcelTempMemoMapper excelTempMemoDao;
    private String batchCode;

    private List<ExcelTempMemo> head;
    private List<ExcelTempData> body;

    public ExcelDataBiz(ExcelTempDataMapper excelTempDataDao,
                        ExcelTempMemoMapper excelTempMemoDao,
                         String batchCode) {

        this.excelTempDataDao = excelTempDataDao;
        this.excelTempMemoDao = excelTempMemoDao;
        this.batchCode = batchCode;
    }

    public List<ExcelTempMemo> getHead() {
        if (ObjectUtils.isEmpty(head)) {
            head = excelTempMemoDao.selectMomeByBatchCode(batchCode);
        }
        return head;
    }

    public List<ExcelTempData> getBody() {
        if (ObjectUtils.isEmpty(body)) {
            body = excelTempDataDao.selectByDataBatchCode(batchCode);
        }
        return body;
    }


}
