package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq403;
import netplus.mall.api.vo.category.Tbmqq403Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq403Mapper extends BaseMapper<Tbmqq403> {
    int deleteByPrimaryKey(String key);

    int insert(Tbmqq403 record);

    int insertSelective(Tbmqq403 record);

    Tbmqq403 selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(Tbmqq403 record);

    int updateByPrimaryKey(Tbmqq403 record);

    Tbmqq403 getTwoLevelClanameInfo(Map<String, Object> map);

    List<Tbmqq403> getTwoLevelInfoList(Map<String, Object> map);

    List<Tbmqq403> getAllTwoLevelInfoList();

    List<Tbmqq403Vo> getAllCategory();

    List<String> getTwoLevelClaNameList(Map<String, Object> map);

    List<Tbmqq403> getTwoLevelClaAndOneLevelClaList(Map<String, Object> map);

    void batchInsert(List<Tbmqq403> list);

    int deleteAll();

}