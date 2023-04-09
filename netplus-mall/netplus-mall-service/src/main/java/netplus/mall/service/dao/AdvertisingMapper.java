package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.mall.api.pojo.ygmalluser.Advertising;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertisingMapper extends BaseMapper<Advertising> {
    int deleteByPrimaryKey(String id);

    int insert(Advertising record);

    int insertSelective(Advertising record);

    Advertising selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Advertising record);

    int updateByPrimaryKey(Advertising record);

    Integer getAdvertisingMaxOrder(@Param("statuses") List<Integer> statuses);

    int getAdvertisingCountByStatus(@Param("status") Integer status);

    int moveNumBack(@Param("num") Integer num, @Param("moveSize") int moveSize);

    Advertising getLeftPublishedAdvertising(@Param("num") Integer num);

    Advertising getRightPublishedAdvertising(@Param("num") Integer num);

    List<Advertising> listAdvertising();

    List<Advertising> selectByIds(@Param("ids") List<String> ids);

    int deleteByIds(@Param("ids") List<String> ids);

    int batchUpdateByIds(Map param);

    int updateByIdAndStatus(Advertising advertising);

    List<Advertising> listAdvertisingByStatus(int statusCode);
}