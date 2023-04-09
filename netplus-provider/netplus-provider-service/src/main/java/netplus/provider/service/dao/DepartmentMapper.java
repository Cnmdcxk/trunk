package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
    int deleteByPrimaryKey(String depno);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String depno);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int batchInsert (List<Department> list);

    int deleteAll();
}