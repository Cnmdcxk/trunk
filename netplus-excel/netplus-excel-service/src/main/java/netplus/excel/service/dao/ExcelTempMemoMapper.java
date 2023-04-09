package netplus.excel.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.excel.api.pojo.ExcelTempMemo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExcelTempMemoMapper extends BaseMapper<ExcelTempMemo> {
    int deleteByPrimaryKey(Long id);

    int insert(ExcelTempMemo record);

    int insertSelective(ExcelTempMemo record);

    ExcelTempMemo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExcelTempMemo record);

    int updateByPrimaryKey(ExcelTempMemo record);

    int bulkInsert(List<ExcelTempMemo> records);

    List<ExcelTempMemo> selectMomeByBatchCode(String batchCode);


}