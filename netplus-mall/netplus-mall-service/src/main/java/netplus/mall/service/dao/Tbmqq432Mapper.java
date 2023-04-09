package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq432;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Tbmqq432Mapper extends BaseMapper<Tbmqq432> {
    int deleteByPrimaryKey(String goodid);

    int insert(Tbmqq432 record);

    int insertSelective(Tbmqq432 record);

    Tbmqq432 selectByPrimaryKey(String goodid);

    int updateByPrimaryKeySelective(Tbmqq432 record);

    int updateByPrimaryKeyWithBLOBs(Tbmqq432 record);

    int updateByPrimaryKey(Tbmqq432 record);
}