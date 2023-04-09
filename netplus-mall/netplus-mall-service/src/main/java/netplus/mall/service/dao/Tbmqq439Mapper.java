package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq439;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq439Mapper extends BaseMapper<Tbmqq439> {
    int deleteByPrimaryKey(String code);

    int insert(Tbmqq439 record);

    int insertSelective(Tbmqq439 record);

    Tbmqq439 selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Tbmqq439 record);

    int updateByPrimaryKey(Tbmqq439 record);

    List<Tbmqq439> getAllList(Map<String,Object> mapParam);

    int getCount(Map<String,Object> mapParam);

    Tbmqq439 getByCode(String code);

    int deleteByCode(String code);
}