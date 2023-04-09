package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.vo.Tbmqq435Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq435Mapper extends BaseMapper<Tbmqq435> {
    int deleteByPrimaryKey(Tbmqq435 key);

    int insert(Tbmqq435 record);

    int insertSelective(Tbmqq435 record);

    Tbmqq435 selectByPrimaryKey(Tbmqq435 key);

    int updateByPrimaryKeySelective(Tbmqq435 record);

    int updateByPrimaryKey(Tbmqq435 record);

    List<Tbmqq435Vo> getSupplierGoodPicList(Map<String, Object> map);

    int batchInsert(List<Tbmqq435> list);

    int delGoodPic(Map<String, Object> map);

    List<Tbmqq435Vo> getGoodPicList(String goodId);

}