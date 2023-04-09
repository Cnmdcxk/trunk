package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.SysLog;
import netplus.provider.api.request.apilog.LoginLogRequest;
import netplus.provider.api.vo.apilog.SysLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户日志 Mapper 接口
 * </p>
 *
 * @author jacky
 * @since 2021-05-07
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    List<SysLogVo> getLoginLogPageList(Map<String,Object> map);

    int getLoginLogCount(Map<String,Object> map);
}
