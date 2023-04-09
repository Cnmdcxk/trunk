package netplus.provider.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExportRest;
import netplus.provider.api.enums.ApiLogTypeEnums;
import netplus.provider.api.enums.LogTypeEnums;
import netplus.provider.api.request.apilog.BusinessLogRequest;
import netplus.provider.api.request.apilog.LoginLogRequest;
import netplus.provider.api.vo.apilog.ApiLogVo;
import netplus.provider.api.vo.apilog.SysLogVo;
import netplus.provider.service.dao.ApiLogMapper;
import netplus.provider.service.dao.SysLogMapper;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiLogBiz {

    @Autowired
    ApiLogMapper apiLogMapper;

    @Autowired
    SysLogMapper sysLogMapper;

    @Autowired
    ExportRest exportRest;


    public PageBean<ApiLogVo> getBusinessLogPage(BusinessLogRequest request) {
        PageBean<ApiLogVo> pageBean=new PageBean<>();
        List<ApiLogVo> list=apiLogMapper.getBusinessLogPageList(request);
        list.forEach(item -> {
            item.setLogTypeStr(ApiLogTypeEnums.getName(item.getLogType().toString()));
            item.setCreateDateStr(DateUtil.format(item.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        });

        int count = apiLogMapper.getBusinessLogCount(request);

        List<Integer> types=apiLogMapper.getLogTypeList(request);
        List<KeyValue> typeKeyValues = types.stream().map(typeCode -> {
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(typeCode.toString());
            keyValue.setValue(ApiLogTypeEnums.getName(typeCode.toString()));
            return keyValue;
        }).collect(Collectors.toList());

        pageBean.setItems(list);
        pageBean.setTotalCount(count);
        pageBean.addResultMap("typeKeyValues",typeKeyValues);
        return pageBean;
    }

    public PageBean<SysLogVo> getLoginLogPage(LoginLogRequest request) {

        Map<String,Object> map= ObjectUtil.transBeanToMap(request);
        if(!StringUtils.isEmpty(request.getSearchKey())){
            List<String> logTypeList = LogTypeEnums.getContainsNameCode(request.getSearchKey());
            map.put("logTypeList",logTypeList);
        }

        PageBean<SysLogVo> pageBean=new PageBean<>();
        List<SysLogVo> list=sysLogMapper.getLoginLogPageList(map);
        list.forEach(item -> {
            item.setLogTypeStr(LogTypeEnums.getName(item.getLogType().toString()));
            item.setEnterTimeStr(DateUtil.format(item.getEnterTime(),"yyyy-MM-dd HH:mm:ss"));
            item.setOutTimeStr(DateUtil.format(item.getOutTime(),"yyyy-MM-dd HH:mm:ss"));
        });
        int count = sysLogMapper.getLoginLogCount(map);

        pageBean.setItems(list);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    public String exportBusinessLog(BusinessLogRequest request) throws IOException {
        List<ApiLogVo> list=apiLogMapper.getBusinessLogPageList(request);
        list.forEach(item -> {
            item.setLogTypeStr(ApiLogTypeEnums.getName(item.getLogType().toString()));
            item.setCreateDateStr(DateUtil.format(item.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        });

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(list);
        genExcelRequest.setTemplateName("tmp_business_log");
        genExcelRequest.setSheetName("业务日志明细导出");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public String exportLoginLog(LoginLogRequest request) throws IOException {
        Map<String,Object> map= ObjectUtil.transBeanToMap(request);
        if(!StringUtils.isEmpty(request.getSearchKey())){
            List<String> logTypeList = LogTypeEnums.getContainsNameCode(request.getSearchKey());
            map.put("logTypeList",logTypeList);
        }
        List<SysLogVo> list=sysLogMapper.getLoginLogPageList(map);
        list.forEach(item -> {
            item.setLogTypeStr(LogTypeEnums.getName(item.getLogType().toString()));
            item.setEnterTimeStr(DateUtil.format(item.getEnterTime(),"yyyy-MM-dd HH:mm:ss"));
            item.setOutTimeStr(DateUtil.format(item.getOutTime(),"yyyy-MM-dd HH:mm:ss"));
        });

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(list);
        genExcelRequest.setTemplateName("tmp_login_log");
        genExcelRequest.setSheetName("登录日志明细导出");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }
}
