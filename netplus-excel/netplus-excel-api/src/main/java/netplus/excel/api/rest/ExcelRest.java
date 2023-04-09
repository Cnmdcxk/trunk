package netplus.excel.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.UploadExcelRequest;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.excel.api.vo.UploadResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * excel 通用处理接口
 *
 * @author lhp
 */
@FeignClient(
        value = "netplus-excel-service",
        url = "${service.netplus-excel-service}",
//        url = "http://localhost:20022",
        contextId = "excel",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ExcelRest {

    /**
     * 倒入excel数据
     */
    @RequestMapping(value = Urls.uploadExcelData, method = RequestMethod.POST)
    int uploadExcelData(UploadExcelRequest request);

    /**
     * 根据批次号，查询数据实发可用
     */
    @RequestMapping(value = Urls.getExcelData, method = RequestMethod.POST)
    ExcelDataVo getExcelData(GetExcelDataRequest getExcelDataRequest);


    //
    @RequestMapping(value = Urls.uploadExcel, method = RequestMethod.POST)
    UploadResultVo uploadExcel(ExcelRequest excelRequest);

}
