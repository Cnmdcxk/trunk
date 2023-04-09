package netplus.excel.service.controller;


import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.excel.api.request.ExportExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExportRest;
import netplus.excel.api.rest.Urls;
import netplus.excel.service.biz.ExportBiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Api(tags="excel导出接口文档")
@RestController
public class ExportController extends BasedController implements ExportRest {

    @Autowired
    private ExportBiz exportBiz;

    @PostMapping(Urls.genExcel)
    public String genExcel(@RequestBody GenExcelRequest genExcelRequest) throws IOException {
        return exportBiz.genExcel(getAppID(), genExcelRequest);
    }

    @PostMapping(Urls.genExcelWithHead)
    public String genExcelWithHead(@RequestBody GenExcelRequest genExcelRequest) throws IOException {
        return exportBiz.genExcelWithHead(getAppID(), genExcelRequest,genExcelRequest.getHead());
    }

    @Override
    public String exportExcel(@RequestBody ExportExcelRequest request) throws IOException {
        return exportBiz.genExcelUrl(getAppID(), request);
    }

}
