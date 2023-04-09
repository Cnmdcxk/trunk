package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq407;
import netplus.mall.api.pojo.ygmalluser.Tbmqq407Key;
import netplus.mall.api.vo.Tbmqq407Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq407Mapper extends BaseMapper<Tbmqq407> {
    int deleteByPrimaryKey(Tbmqq407Key key);

    int insert(Tbmqq407 record);

    int insertSelective(Tbmqq407 record);

    Tbmqq407 selectByPrimaryKey(Tbmqq407Key key);

    int updateByPrimaryKeySelective(Tbmqq407 record);

    int updateByPrimaryKey(Tbmqq407 record);

    List<Tbmqq407Vo> selectBasicPicLib(Map<String,Object> map);

    List<Tbmqq407Vo> getBasicPicLib(Map<String,Object> map);

    int selectPicCount(Map<String,Object> map);

    List<Tbmqq407Vo> getPicByMatrlTm(List<String> matrlTmList);

    void batchInsert(List<Tbmqq407> list);
}