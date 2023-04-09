package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq436;
import netplus.mall.api.vo.GoodsHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq436Mapper extends BaseMapper<Tbmqq436> {
    int insert(Tbmqq436 record);

    int insertSelective(Tbmqq436 record);

    void addGoodLog(String goodId);

    void addGoodLogs(@Param("goodIdList") List<String> goodIdList);

    List<GoodsHistoryVo> getGoodsHistoryList(Map<String, Object> map);

    List<KeyValue> getSupplierName(Map<String, Object> map);

    List<KeyValue> getMatrltm(Map<String, Object> map);

    List<KeyValue> getPono(Map<String, Object> map);
}