package netplus.excel.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.excel.api.pojo.ExcelTempData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExcelTempDataMapper extends BaseMapper<ExcelTempData> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ExcelTempData record);

    ExcelTempData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExcelTempData record);

    int updateByPrimaryKey(ExcelTempData record);

    int bulkInsert(List<ExcelTempData> records);

    List<ExcelTempData> selectByDataBatchCode(String batchCode);
}