package netplus.provider.service.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import netplus.component.authbase.utils.ToolUtils;
import netplus.provider.api.enums.EnumLoginCode;
import netplus.provider.api.enums.LogTypeEnums;
import netplus.provider.api.pojo.ygmalluser.SysLog;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.dao.SysLogMapper;
import netplus.utils.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户日志 服务实现类
 * </p>
 *
 * @author jacky
 * @since 2021-05-07
 */
@Service
public class SysLogBiz extends ServiceImpl<SysLogMapper, SysLog> {

    @Autowired
    private SysLogMapper sysLogDao;

    /**
     * 保存日志
     * @param request
     * @param userVo
     * @param resultCode
     * @param pageUrl
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveLog(Tbdu01Vo userVo, String resultCode, String pageUrl) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setPageUrl(pageUrl);
        sysLog.setIpAddress(ToolUtils.getIpAddress(request));
        sysLog.setExt1(userVo.getName());
        sysLog.setSessionid(request.getRequestedSessionId());
        sysLog.setIeVersion(request.getHeader("User-Agent"));
        if(EnumLoginCode.OK.getValue().equals(resultCode)){
            sysLog.setLogType(Long.valueOf(LogTypeEnums.LOGIN.getCode()));
        }else{
            sysLog.setLogType(Long.valueOf(LogTypeEnums.ERROR.getCode()));
        }
        sysLog.setUserNo(userVo.getUserno());
        sysLog.setEnterTime(DateUtil.curTimestamp());
        sysLogDao.insert(sysLog);
    }
}
