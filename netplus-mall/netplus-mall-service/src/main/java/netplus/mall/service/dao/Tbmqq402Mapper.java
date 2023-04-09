package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq402;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq402Mapper extends BaseMapper<Tbmqq402> {
    int deleteByPrimaryKey(String key);

    int insert(Tbmqq402 record);

    int insertSelective(Tbmqq402 record);

    Tbmqq402 selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(Tbmqq402 record);

    int updateByPrimaryKey(Tbmqq402 record);

    List<KeyValue> getOneLevelClanameListKV(Map<String, Object> map);

    Tbmqq402 getOneLevelClanameInfo(Map<String, Object> map);

    List<String> getonelevelclaname(String categoryname);

    List<String> getOneLevelClaNameList(Map<String, Object> map);

    List<Tbmqq402> getOneLevelClaAndCategory(Map<String, Object> map);

    void batchInsert(List<Tbmqq402> list);

    int deleteAll();
}