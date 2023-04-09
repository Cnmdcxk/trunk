package netplus.excel.service.controller;


import com.google.gson.Gson;
import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.UploadExcelRequest;
import netplus.excel.api.rest.ExcelRest;
import netplus.excel.api.rest.GetExcelDataRequest;
import netplus.excel.api.rest.Urls;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.excel.api.vo.UploadResultVo;
import netplus.excel.service.biz.ExcelDataBiz;
import netplus.excel.service.biz.UploadExcelBiz;
import netplus.excel.service.dao.ExcelTempDataMapper;


import netplus.excel.service.dao.ExcelTempMemoMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lhp on 2017/5/2.
 */

@Api(tags="Excel解析接口文档")
@RestController
public class ExcelController extends BasedController implements ExcelRest {


    protected static final Log logger = LogFactory.getLog(ExcelController.class);


    @Autowired
    private ExcelTempDataMapper excelTempDataDao;

    @Autowired
    private ExcelTempMemoMapper excelTempMemoDao;

    @Autowired
    private UploadExcelBiz uploadExcelBiz;

    /**
     * 倒入excel数据
     */
    @Anonymous
    @RequestMapping(value = Urls.uploadExcelData, method = RequestMethod.POST)
    @Override
    public int uploadExcelData(@RequestBody UploadExcelRequest request) {
        excelTempDataDao.bulkInsert(request.getBody());
        return excelTempMemoDao.bulkInsert(request.getHead());
    }

    /**
     * 导入excel数据
     */
    @Anonymous
    @Override
    @RequestMapping(value = Urls.uploadExcel, method = RequestMethod.POST)
    public UploadResultVo uploadExcel(@RequestBody ExcelRequest excelRequest){

        logger.info(String.format("接受到uploadExcel请求，参数：%s", new Gson().toJson(excelRequest)));

        String appId = getAppID();
        return uploadExcelBiz.save(appId, excelRequest);
    }

    @Anonymous
    @RequestMapping(value = Urls.getExcelData, method = RequestMethod.POST)
    @Override
    public ExcelDataVo getExcelData(@RequestBody GetExcelDataRequest getExcelDataRequest) {

        if (StringUtils.isEmpty(getExcelDataRequest.getBatchCode()))
            throw new NetPlusException("查询批次不能为空");

        ExcelDataVo vo = new ExcelDataVo();
        ExcelDataBiz biz = new ExcelDataBiz(excelTempDataDao, excelTempMemoDao, getExcelDataRequest.getBatchCode());
        vo.setBatchCode(getExcelDataRequest.getBatchCode());
        vo.setBody(biz.getBody());
        vo.setHead(biz.getHead());
        return vo;
    }
}
