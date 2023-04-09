package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq438;
import netplus.mall.api.pojo.ygmalluser.Tbmqq438Key;
import netplus.mall.api.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Tbmqq438Mapper extends BaseMapper<Tbmqq438> {
    int deleteByPrimaryKey(Tbmqq438Key key);

    int insert(Tbmqq438 record);

    int insertSelective(Tbmqq438 record);

    Tbmqq438 selectByPrimaryKey(Tbmqq438Key key);

    int updateByPrimaryKeySelective(Tbmqq438 record);

    int updateByPrimaryKey(Tbmqq438 record);

    List<String> getMatrlIdBySupplierNo (String supplierNo);

    void batchInsert(List<Tbmqq438> list);

    int updateSale (Tbmqq438 tbmqq438);

    List<GoodsVo> getRankingData();

    List<GoodsVo> getShoppingList();



}