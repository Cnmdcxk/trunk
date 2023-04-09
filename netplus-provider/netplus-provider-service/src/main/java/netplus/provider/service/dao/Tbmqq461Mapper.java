package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.vo.Tbmqq461Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq461Mapper extends BaseMapper<Tbmqq461> {
    int deleteByPrimaryKey(String code);

    int insert(Tbmqq461 record);

    int insertSelective(Tbmqq461 record);

    Tbmqq461 selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Tbmqq461 record);

    int updateByPrimaryKey(Tbmqq461 record);

    List<Tbmqq461Vo> getAll(Map<String, Object> mapParam);

    int getCount(Map<String, Object> mapParam);

    Tbmqq461 getByCode(Map<String, Object> mapParam);
}