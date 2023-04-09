package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq427;
import netplus.mall.api.pojo.ygmalluser.Tbmqq427Key;
import netplus.mall.api.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq427Mapper extends BaseMapper<Tbmqq427> {
    int deleteByPrimaryKey(Tbmqq427Key key);

    int insert(Tbmqq427 record);

    int insertSelective(Tbmqq427 record);

    Tbmqq427 selectByPrimaryKey(Tbmqq427Key key);

    int updateByPrimaryKeySelective(Tbmqq427 record);

    int updateByPrimaryKey(Tbmqq427 record);

    List<GoodsVo> getTop(Map<String, Object> map);

    int clearData();
}