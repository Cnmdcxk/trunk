package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq405;
import netplus.mall.api.vo.Tbmqq405Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq405Mapper extends BaseMapper<Tbmqq405> {
    int deleteByPrimaryKey(String matrlno);

    int insert(Tbmqq405 record);

    int insertSelective(Tbmqq405 record);

    Tbmqq405 selectByPrimaryKey(String matrlno);

    int updateByPrimaryKeySelective(Tbmqq405 record);

    int updateByPrimaryKey(String record);

    List<Tbmqq405Vo> getMaterialList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    int updateByPrimaryKey(Tbmqq405Vo record);

    //获取物料号分类
    List<Tbmqq405Vo> getMaterialInfo(Map<String, Object> map);

    List<KeyValue> getTwoLevelClaNameKV(Map<String, Object> map);

    List<Tbmqq405Vo> getAllMatrlList();

    int batchInsert(List<Tbmqq405> list);

    List<Tbmqq405> getMatrlNo(Map<String,Object> map);

    int deleteAll();

    int selectTwoClaname(Map<String,Object> map);

    List<Tbmqq405Vo> getMatrlByIdsAndSupplierNo(Map<String, Object> map);

}