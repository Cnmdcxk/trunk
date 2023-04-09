package netplus.provider.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.provider.api.pojo.ygmalluser.Tbmqq439;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq439Mapper extends BaseMapper<Tbmqq439> {
    /**
     * 根据主键删除
     *
     * @param code
     * @return
     */
    int deleteByPrimaryKey(String code);

    /**
     * 添加
     *
     * @param record
     * @return
     */
    int insert(Tbmqq439 record);

    /**
     * 存在值添加
     *
     * @param record
     * @return
     */
    int insertSelective(Tbmqq439 record);

    /**
     * 根据编号查询
     *
     * @param code
     * @return
     */
    Tbmqq439 selectByPrimaryKey(String code);

    /**
     * 存在值修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Tbmqq439 record);

    /**
     * 全部修改
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Tbmqq439 record);

    /**
     * 获取全部数据
     *
     * @param map
     * @return
     */
    List<Tbmqq439> getList(Map<String, Object> map);
}