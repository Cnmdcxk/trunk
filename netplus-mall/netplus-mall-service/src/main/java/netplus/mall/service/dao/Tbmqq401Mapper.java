package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq401;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq401Mapper extends BaseMapper<Tbmqq401> {
    int deleteByPrimaryKey(String categoryname);

    int insert(Tbmqq401 record);

    int insertSelective(Tbmqq401 record);

    Tbmqq401 selectByPrimaryKey(String categoryname);

    int updateByPrimaryKeySelective(Tbmqq401 record);

    int updateByPrimaryKey(Tbmqq401 record);

    List<KeyValue> getCategoryNameListKV(Map<String, Object> map);

    List<String> getCategoryNameList(Map<String, Object> map);

    void batchInsert(List<Tbmqq401> list);

    int deleteAll();
}