package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq431;
import netplus.mall.api.vo.GoodsGroupVo;
import netplus.mall.api.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq431Mapper extends BaseMapper<Tbmqq431> {
    int insert(Tbmqq431 record);

    int insertSelective(Tbmqq431 record);

    List<GoodsGroupVo> getGoodsGroupList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    List<KeyValue> getCategoryNameListKV(Map<String, Object> map);
    List<KeyValue> getBrandListKV(Map<String, Object> map);

    List<GoodsVo> getGroupGoodList(Map<String, Object> map);

    int batchDeleteGoodsGroup(List groups);

    int deleteGoodsGroup(String groupNo);




}