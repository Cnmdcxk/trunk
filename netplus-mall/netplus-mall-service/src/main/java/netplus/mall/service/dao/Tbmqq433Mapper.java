package netplus.mall.service.dao;

import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq433;
import netplus.mall.api.pojo.ygmalluser.Tbmqq433Key;
import netplus.mall.api.vo.shoppingCart.Tbmqq433Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq433Mapper {
    int deleteByPrimaryKey(Tbmqq433Key key);

    int insert(Tbmqq433 record);

    int insertSelective(Tbmqq433 record);

    Tbmqq433 selectByPrimaryKey(Tbmqq433Key key);

    int updateByPrimaryKeySelective(Tbmqq433 record);

    int updateByPrimaryKey(Tbmqq433 record);

    void batchInsert(List<Tbmqq433> list);

    List<Tbmqq433Vo> getMyShoppingCartList(Map<String, Object> map);

    int delMyShoppingCart(Map<String, Object> map);

    List<KeyValue> getCategoryNameFilter(Map<String, Object> map);

    List<KeyValue> getBrandFilter(Map<String, Object> map);

    int getShoppingCartCount(String userNo);

    Tbmqq433Vo getMyShoppingCartByGoodId(Map<String, Object> map);

    int update(Tbmqq433Vo tbmqq433Vo);

    void updateShoppingCartRemark(Map<String, Object> mapParam);

    List<Tbmqq433Vo> getMyShoppingCartRemark(Map<String, Object> mapParam);
}