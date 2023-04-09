package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.request.GetSupplierBizContactRequest;
import netplus.provider.api.vo.Tbdu01Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbdu01Mapper extends BaseMapper<Tbdu01> {
    int deleteByPrimaryKey(String userno);

    int insertSelective(Tbdu01 record);

    Tbdu01 selectByPrimaryKey(String userno);

    List<String> selectAllBUserNo();

    int batchInsert(List<Tbdu01> tbdu01List);

    int batchUpdate(Tbdu01 tbdu01);

    int batchDelete(List<String> userList);

    int updateByPrimaryKeySelective(Tbdu01 record);

    int updateByPrimaryKey(Tbdu01 record);

    Tbdu01Vo getUserByUserNoAndPassword(@Param("userNo") String userNo, @Param("password") String password);

    Tbdu01Vo getUserByUserNoAndUserType(@Param("userNo") String userNo, @Param("userType") String userType);

    List<Tbdu01Vo> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    int updateIsRole(@Param("userNo") String userNo, @Param("isRole") String isRole, @Param("oldIsRole") String oldIsRole);

    int updateIsActive(@Param("userNo") String userNo, @Param("isActive") String isActive, @Param("oldIsActive") String oldIsActive);

    Tbdu01 getSupplierByPhone(String mobile);

    int updateSupplierPasswordByPhone(@Param("mobile") String mobile, @Param("password") String password);

    Tbdu01Vo getSupplierNo(String supplierNo);

    List<Tbdu01Vo> getSupplierNoList(@Param("supplierNoList") List<String> supplierNoList);

    Tbdu01Vo getSupplierByCompName(String compName);

    List<Tbdu01Vo> getConsignee(@Param("keyword") String keyword, @Param("deptNo") String deptNo);

    Tbdu01Vo getConsigneeByUserNo(String userNo);

    Tbdu01Vo getUserByUserNo(String userNo);

    Map<String, String> getSupplierBizContact(GetSupplierBizContactRequest request);
}