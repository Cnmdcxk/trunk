package netplus.mall.service.dao;

import netplus.mall.api.pojo.ygmalluser.Futuregood;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FuturegoodMapper {
    int deleteByPrimaryKey(String goodid);

    int insert(Futuregood record);

    int insertSelective(Futuregood record);

    Futuregood selectByPrimaryKey(String goodid);

    int updateByPrimaryKeySelective(Futuregood record);

    int updateByPrimaryKey(Futuregood record);

    int deleteFutureGoodByPoNoPk(@Param("poNo") String poNo);

    void batchInsert(List<Futuregood> list);
}