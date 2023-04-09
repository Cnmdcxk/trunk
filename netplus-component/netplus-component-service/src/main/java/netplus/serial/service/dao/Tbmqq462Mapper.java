package netplus.serial.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.serial.api.pojo.db.Tbmqq462;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Tbmqq462Mapper extends BaseMapper<Tbmqq462> {
    int deleteByPrimaryKey(String code);

    int insert(Tbmqq462 record);

    int insertSelective(Tbmqq462 record);

    Tbmqq462 selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Tbmqq462 record);

    int updateByPrimaryKey(Tbmqq462 record);
}