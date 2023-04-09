package netplus.provider.service.dao;

import netplus.provider.api.pojo.ygmalluser.ApiLog;
import netplus.provider.api.request.apilog.BusinessLogRequest;
import netplus.provider.api.vo.apilog.ApiLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApiLog record);

    int insertSelective(ApiLog record);

    ApiLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiLog record);

    int updateByPrimaryKey(ApiLog record);

    List<ApiLogVo> getBusinessLogPageList(BusinessLogRequest request);

    int getBusinessLogCount(BusinessLogRequest request);

    List<Integer> getLogTypeList(BusinessLogRequest request);
}