package netplus.excel.api.rest;


import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.excel.api.request.ExportExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@FeignClient(
        value = "netplus-excel-service",
        url = "${service.netplus-excel-service}",
//        url = "http://127.0.0.1:20022",

        contextId = "export",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ExportRest {

    @PostMapping(Urls.genExcel)
    String genExcel(GenExcelRequest genExcelRequest) throws IOException;

    @PostMapping(Urls.genExcelWithHead)
    String genExcelWithHead(GenExcelRequest genExcelRequest) throws IOException;

    @PostMapping(Urls.exportExcel)
    String exportExcel(ExportExcelRequest request) throws IOException;
}
