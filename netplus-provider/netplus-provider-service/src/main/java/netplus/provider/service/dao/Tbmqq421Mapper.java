package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbmqq421;
import netplus.provider.api.pojo.ygmalluser.Tbmqq421Key;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq421Mapper extends BaseMapper<Tbmqq421> {
    int deleteByPrimaryKey(Tbmqq421Key key);

    int insert(Tbmqq421 record);

    int insertSelective(Tbmqq421 record);

    Tbmqq421 selectByPrimaryKey(Tbmqq421Key key);

    int updateByPrimaryKeySelective(Tbmqq421 record);

    int updateByPrimaryKey(Tbmqq421 record);

    List<Tbmqq421> ispermission(String rolecode);

    void bulkCreate(List<Tbmqq421> list);

    int delByRoleCode(Map<String, Object> map);
}