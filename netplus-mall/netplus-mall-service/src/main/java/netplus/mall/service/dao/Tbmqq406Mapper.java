package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq406;
import netplus.mall.api.pojo.ygmalluser.Tbmqq406Key;
import netplus.mall.api.vo.CategoryVo;
import netplus.mall.api.vo.Tbmqq406Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq406Mapper extends BaseMapper<Tbmqq406> {
    int deleteByPrimaryKey(Tbmqq406Key key);

    int insert(Tbmqq406 record);

    int insertSelective(Tbmqq406 record);

    Tbmqq406 selectByPrimaryKey(Tbmqq406Key key);

    int updateByPrimaryKeySelective(Tbmqq406 record);

    int updateByPrimaryKey(Tbmqq406 record);

    List<Tbmqq406Vo> getSkuMaterialList(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    List<Tbmqq406Key> getTbmqq406Key();

    List<Tbmqq406> getMatrlNo(Map<String, Object> map);


    void batchInsert(List<Tbmqq406> list);

    CategoryVo getCategoryByGoodsno(String goodsNo);



    Tbmqq406Vo getMatrlNoByGoodNo(Map<String, Object> map);

    int update406ByPrimaryKeySelective(Tbmqq406 tbmqq406);

    int selectGoodNo(Map<String,Object> map);
}