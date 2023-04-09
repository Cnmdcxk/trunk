package netplus.mall.service.dao;

import netplus.mall.api.pojo.ygmalluser.Approve;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApproveMapper {
    int deleteByPrimaryKey(String approvecode);

    int insert(Approve record);

    int insertSelective(Approve record);

    Approve selectByPrimaryKey(String approvecode);

    int updateByPrimaryKeySelective(Approve record);

    int updateByPrimaryKey(Approve record);
}