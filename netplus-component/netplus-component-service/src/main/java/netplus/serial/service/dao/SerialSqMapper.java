package netplus.serial.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.serial.api.pojo.SerialSq;
import org.apache.ibatis.annotations.Param;

public interface SerialSqMapper extends BaseMapper<SerialSq> {
//    int deleteByPrimaryKey(Integer id);

//    int insertSelective(SerialSq record);

//    SerialSq selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(SerialSq record);

//    int updateByPrimaryKey(SerialSq record);

//    int batchInsert(List<SerialSq> records);

    /**
     * 读取最新序列
     * @param serialName
     * @param appId
     * @return
     */
    Integer selectByName(@Param("serialName") String serialName, @Param("appId") String appId);

    Integer updateByName(@Param("serialName") String serialName, @Param("appId") String appId);

    Integer updateByNameToSq(@Param("serialName") String serialName, @Param("appId") String appId, @Param("startSq") Integer startSq);

}