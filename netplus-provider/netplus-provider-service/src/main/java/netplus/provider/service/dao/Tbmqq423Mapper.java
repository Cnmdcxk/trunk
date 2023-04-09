package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.provider.api.pojo.ygmalluser.Tbmqq423;
import netplus.provider.api.vo.Tbmqq423Parent;
import netplus.provider.api.vo.Tbmqq423Vo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq423Mapper extends BaseMapper<Tbmqq423> {
    int deleteByPrimaryKey(String code);

    int insert(Tbmqq423 record);

    int insertSelective(Tbmqq423 record);

    Tbmqq423 selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(Tbmqq423 record);

    int updateByPrimaryKey(Tbmqq423 record);

    List<Tbmqq423Vo> getList(Map<String,Object> map);

    List<KeyValue> getparentcodeList(Map<String,Object> map);

    int getjurisdictionCount(Map<String, Object> map);

    List<Tbmqq423Vo> getPrivilegeInfoByParam(Map<String, Object> map);

    List<KeyValue> getprivilegetypeList(Map<String,Object> map);

    List<Tbmqq423Parent> getListPermissionsParent();

    List<Tbmqq423> getListPermissionsChildren(String code);

    List<String> getUserPrivileges (Map<String, Object> map);

    List<Tbmqq423Vo> getRolePrivileges(Map<String, Object> map);

    List<Tbmqq423Vo> getRoleOwnPrivileges(Map<String, Object> map);

    List<Tbmqq423Vo> getUserMenuList (Map<String, Object> map);

    List<Tbmqq423Vo> getPrivilegeListByType(Map<String, Object> map);
}