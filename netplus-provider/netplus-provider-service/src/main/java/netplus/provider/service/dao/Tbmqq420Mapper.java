package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbmqq420;
import netplus.provider.api.vo.Tbmqq420Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq420Mapper extends BaseMapper<Tbmqq420> {
    int deleteByPrimaryKey(String rolecode);

    int insert(Tbmqq420 record);

    int insertSelective(Tbmqq420 record);

    Tbmqq420 selectByPrimaryKey(String rolecode);

    int updateByPrimaryKeySelective(Tbmqq420 record);

    int updateByPrimaryKey(Tbmqq420 record);

    List<Tbmqq420Vo> getall(Map<String, Object> mapParam);

    int getallCount(Map<String, Object> mapParam);

    void deleteByRolecode(String id);

    List<Tbmqq420Vo> getRoleByParam(Map<String, Object> mapParam);

    int modifyRole(Tbmqq420 tbmqq420);

    List<Tbmqq420> islist(String id);

    List<Tbmqq420Vo> getUserOwnRoleByUserNo (@Param("userNo") String userNo);

}