package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq429;
import netplus.mall.api.pojo.ygmalluser.Tbmqq429Key;
import netplus.mall.api.vo.picLib.Tbmqq429Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq429Mapper extends BaseMapper<Tbmqq429> {
    int deleteByPrimaryKey(Tbmqq429Key key);

    int insert(Tbmqq429 record);

    int insertSelective(Tbmqq429 record);

    Tbmqq429 selectByPrimaryKey(Tbmqq429Key key);

    int updateByPrimaryKeySelective(Tbmqq429 record);

    int updateByPrimaryKey(Tbmqq429 record);

    List<Tbmqq429Vo> getGoodPicList(Map<String, Object> map);

    void batchInsert(List<Tbmqq429> list);

    List<Tbmqq429Vo> getPicLibList(Map<String, Object> map);

    List<String> getMatrlNoPicNum(Map<String, Object> map);

    List<Tbmqq429Vo> getPicByMatrlTmAndSupplierNo(Map<String, Object> map);

    int delSame(Map<String,Object> map);

    int delBySupplierNoAndMatrlno(Map<String, Object> map);

    int updatePic(Tbmqq429 record);
}