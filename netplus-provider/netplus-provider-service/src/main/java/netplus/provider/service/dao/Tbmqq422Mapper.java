package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbmqq422;
import netplus.provider.api.pojo.ygmalluser.Tbmqq422Key;
import netplus.provider.api.vo.Tbmqq422Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq422Mapper extends BaseMapper<Tbmqq422> {
    int deleteByPrimaryKey(Tbmqq422Key key);

    int insert(Tbmqq422 record);

    int insertSelective(Tbmqq422 record);

    Tbmqq422 selectByPrimaryKey(Tbmqq422Key key);

    int updateByPrimaryKeySelective(Tbmqq422 record);

    int updateByPrimaryKey(Tbmqq422 record);

    List<Tbmqq422> islist(String id);


    void batchInsert(List<Tbmqq422Vo> list);

    void deleteByUserNo(@Param("userNo") String userNo);

    List<String> selectRoleList(Map<String,Object> map);

    List<Tbmqq422> selectSupplyInfo(Map<String,Object> map);

    int initRole(Map<String,Object> map);

    List<String> getRoleList();

    List<Tbmqq422Vo> getUserRoleByUserNo(@Param("userNoList") List<String> userNoList);

    List<String> getUserRoleList(@Param("userNo") String userNo);
}