package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq437;
import netplus.mall.api.pojo.ygmalluser.Tbmqq437Key;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq437Mapper extends BaseMapper<Tbmqq437> {
    int deleteByPrimaryKey(Tbmqq437Key key);

    int insert(Tbmqq437 record);

    int insertSelective(Tbmqq437 record);

    Tbmqq437 selectByPrimaryKey(Tbmqq437Key key);

    int updateByPrimaryKeySelective(Tbmqq437 record);

    int updateByPrimaryKey(Tbmqq437 record);

    List<Tbmqq437> getUserViewHistory(String userNo);

    int getViewHistoryCountByGoodId(Map<String, Object> map);



}