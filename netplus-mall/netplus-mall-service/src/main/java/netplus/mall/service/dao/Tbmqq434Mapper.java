package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq434;
import netplus.mall.api.pojo.ygmalluser.Tbmqq434Key;
import netplus.mall.api.vo.goodCollect.GoodCollectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq434Mapper extends BaseMapper<Tbmqq434> {
    int deleteByPrimaryKey(Tbmqq434Key key);

    int insert(Tbmqq434 record);

    int insertSelective(Tbmqq434 record);

    Tbmqq434 selectByPrimaryKey(Tbmqq434Key key);

    int updateByPrimaryKeySelective(Tbmqq434 record);

    int updateByPrimaryKey(Tbmqq434 record);

    List<GoodCollectVo> getMyCollectList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    List<KeyValue> getCategoryNameKV(Map<String, Object> map);

    int delMyCollect(Map<String, Object> map);

    List<GoodCollectVo> getMyCollectListByGoodsIds(Map<String, Object> map);

    int batchInsert(List<Tbmqq434> list);

    void updateCollectRemark(Map<String, Object> mapParam);

    List<GoodCollectVo> getMyCollectListRemark(Map<String, Object> mapParam);
}