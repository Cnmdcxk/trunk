package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq443;
import netplus.mall.api.pojo.ygmalluser.Tbmqq443Key;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq443Mapper extends BaseMapper<Tbmqq443> {
    int deleteByPrimaryKey(Tbmqq443Key key);

    int insert(Tbmqq443 record);

    int insertSelective(Tbmqq443 record);

    Tbmqq443 selectByPrimaryKey(Tbmqq443Key key);

    int updateByPrimaryKeySelective(Tbmqq443 record);

    int updateByPrimaryKey(Tbmqq443 record);

    int batchInsert(List<Tbmqq443> list);

    List<Tbmqq443> getAppendix(Map<String, Object> map);
}