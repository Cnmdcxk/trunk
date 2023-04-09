package netplus.provider.service.controller;

import netplus.component.entity.data.PageBean;
import netplus.provider.api.Urls;
import netplus.provider.api.request.apilog.BusinessLogRequest;
import netplus.provider.api.request.apilog.LoginLogRequest;
import netplus.provider.api.vo.apilog.ApiLogVo;
import netplus.provider.api.vo.apilog.SysLogVo;
import netplus.provider.service.biz.ApiLogBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ApiLogController {

    @Autowired
    ApiLogBiz apiLogBiz;

    @PostMapping(Urls.ApiLog.getBusinessLogPage)
    public PageBean<ApiLogVo> getBusinessLogPage(@RequestBody BusinessLogRequest request){
        return apiLogBiz.getBusinessLogPage(request);
    }

    @PostMapping(Urls.ApiLog.getLoginLogPage)
    public PageBean<SysLogVo> getLoginLogPage(@RequestBody LoginLogRequest request){
        return apiLogBiz.getLoginLogPage(request);
    }

    @PostMapping(Urls.ApiLog.exportBusinessLog)
    public String exportBusinessLog(@RequestBody BusinessLogRequest request) throws IOException {
        return apiLogBiz.exportBusinessLog(request);
    }

    @PostMapping(Urls.ApiLog.exportLoginLog)
    public String exportLoginLog(@RequestBody LoginLogRequest request) throws IOException {
        return apiLogBiz.exportLoginLog(request);
    }

}
