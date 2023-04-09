package netplus.excel.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.excel.api.pojo.ExportTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExportTemplateMapper extends BaseMapper<ExportTemplate> {
    int deleteByPrimaryKey(Integer id);

    int insert(ExportTemplate record);

    int insertSelective(ExportTemplate record);

    ExportTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExportTemplate record);

    int updateByPrimaryKey(ExportTemplate record);

    List<ExportTemplate> selectByTemplateName(Map<String, Object> map);
}