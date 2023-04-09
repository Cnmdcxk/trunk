package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.request.goodsInventory.GoodsInventoryPageRequest;
import netplus.mall.api.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq430Mapper extends BaseMapper<Tbmqq430> {
    int deleteByPrimaryKey(String goodid);

    int insert(Tbmqq430 record);

    int insertSelective(Tbmqq430 record);

    Tbmqq430 selectByPrimaryKey(String goodid);

    int updateByPrimaryKeySelective(Tbmqq430 record);

    int updateByPrimaryKey(Tbmqq430 record);

    List<GoodsVo> getMyGoodsList(Map<String, Object> map);

    int getMyGoodsCount(Map<String, Object> map);

    Map<String,Object> getShelfCount(Map<String,Object> map);

    Map<String, Object> getTabCount(Map<String, Object> map);

    List<KeyValue> getCategoryPkListKV(Map<String, Object> map);

    List<KeyValue> getBrandListKV(Map<String, Object> map);

    List<KeyValue> getSupplierListKV(Map<String, Object> map);

    List<KeyValue> getAgentListKV(Map<String, Object> map);

    List<KeyValue> getStatusListKV(Map<String, Object> map);

    int updateGroupNo(String groupNo);

    GoodsVo getGoodsById(String goodid);

    int updateGroupNoByIds(List ids);

    int countIds(Map<String,Object> map);

    List<GoodsVo> getGoodsDetail(Map<String, Object> map);

    List<GoodsVo> getGoodByIds(Map<String, Object> map);

    GoodsVo getSupplierGoodById(Map<String, Object> map);

    int updateGoodInfo(GoodsVo goodsVo);

    List<GoodsVo> search(Map<String, Object> map);

    int searchCount(Map<String, Object> map);

    List<KeyValue> searchOneLevelClaNameKV(Map<String, Object> map);

    List<KeyValue> searchTwoLevelClaNameKV(Map<String, Object> map);

    List<KeyValue> searchCategoryNameKV(Map<String, Object> map);

    List<KeyValue> searchBrandKV(Map<String, Object> map);

    void batchInsert(List<Tbmqq430> list);

    List<String> getGoodUniqueIndexList();

    GoodsVo getGoodIdByGoodNo(Map<String, Object> map);

    List<GoodsVo> getSettleGoodInfo(Map<String, Object> map);

    int updateGoodInvalid(Map<String, Object> map);

    List<Tbmqq430> getGoodByPonoPk (String pono);

    int delGoodByGoodId(Map<String, Object> map);

    List<GoodsVo> getGoodBySupplierNo(String supplierNo);

    List<GoodsVo> getGoodsByMatrltmList(Map<String, Object> map);

    int delInvalidGood();

    List<Tbmqq430> getSupplierOtherPonoGood(@Param("ponoPk") String ponoPk, @Param("supplierNo") String supplierNo);

    List<KeyValue> getGoodsMatrlInfo(GoodsInventoryPageRequest request);

    int getGoodsMatrlCount(GoodsInventoryPageRequest request);

    List<GoodsVo> getGoodByMatrlIdList(Map<String, Object> mapParam);

    List<GoodsVo> getMatrlStatus(Map<String, Object> mapParam);

    List<String> getExpiredGoodIdList();

    List<GoodsVo> getCountPonoInfo(Map<String, Object> map);

    int getCountPonoCount(Map<String, Object> map);

    List<GoodsVo> getPonoDetailInfo(Map<String, Object> map);

    int getPonoDetailInfoCount(Map<String, Object> map);
}