package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq428;
import netplus.mall.api.pojo.ygmalluser.Tbmqq428Key;
import netplus.mall.api.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Tbmqq428Mapper extends BaseMapper<Tbmqq428> {
    int deleteByPrimaryKey(Tbmqq428Key key);

    int insert(Tbmqq428 record);

    int insertSelective(Tbmqq428 record);

    Tbmqq428 selectByPrimaryKey(Tbmqq428Key key);

    int updateByPrimaryKeySelective(Tbmqq428 record);

    int updateByPrimaryKey(Tbmqq428 record);

    List<GoodsVo> getTop();

    int clearData();
}