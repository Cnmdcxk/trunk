package netplus.messaging.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.messaging.api.pojo.ygmalluser.Messaging;
import netplus.messaging.api.vo.MessagingVo;
import netplus.messaging.api.vo.NumCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessagingMapper extends BaseMapper<Messaging> {
    int deleteByPrimaryKey(String id);

    int insert(Messaging record);

    int insertSelective(Messaging record);

    Messaging selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Messaging record);

    int updateByPrimaryKey(Messaging record);

    NumCountVo getAllCount(Map<String, Object> map);

    List<MessagingVo> querPage(Map<String, Object> params);

    int listPageCount(Map<String, Object> map);

    int updateState(Map<String, Object> map);

    int updateAllState(String userid);

    int batchInsert(List<Messaging> messagingList);

    List<String> getMsgGroupByUserId(@Param("userid") String userid);

    List<KeyValue> getCountWithGroup(Map<String, Object> params);
}