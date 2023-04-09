package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq440;
import netplus.mall.api.vo.order.PurchaseOrderDetailExportVo;
import netplus.mall.api.vo.order.Tbmqq440Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq440Mapper extends BaseMapper<Tbmqq440> {
    int deleteByPrimaryKey(String orderno);

    int insert(Tbmqq440 record);

    int insertSelective(Tbmqq440 record);

    Tbmqq440 selectByPrimaryKey(String orderno);

    int updateByPrimaryKeySelective(Tbmqq440 record);

    int updateByPrimaryKey(Tbmqq440 record);

    void batchInsert(List<Tbmqq440Vo> list);

    List<Tbmqq440Vo> getOrderList(Map<String, Object> map);

    List<KeyValue> getDeptKV(Map<String, Object> map);

    List<KeyValue> getStatusKV(Map<String, Object> map);

    List<KeyValue> getBuyerKV(Map<String, Object> map);

    int getOrderCount(Map<String, Object> map);

    int changeOrderStatus(Map<String, Object> map);

    Tbmqq440Vo getOrderInfo(Map<String, Object> map);

    Map<String, Object> getTabCount(Map<String, Object> map);

    List<Tbmqq440Vo> getOrderScheduleQty(@Param("orderNoList") List<String> orderNoList);

    List<Tbmqq440Vo> getInvoiceOrderScheduleQty(@Param("orderNoList") List<String> orderNoList);

    BigDecimal getLineOrderAmount (@Param("linePk") String linePk, @Param("deptNo") String deptNo);

    int updateReceivingDate(@Param("orderNo") String orderNo, @Param("receivingDate") String receivingDate, @Param("receivingTime") String receivingTime);

    int updateOrderScheduleQty(Tbmqq440Vo tbmqq440Vo);

    int updateInvoiceOrderScheduleQty(Tbmqq440Vo tbmqq440Vo);

    List<Tbmqq440Vo> getOrderNoByApproveCode (String approveCode);

    Tbmqq440Vo getOrderAmtAndQty(String orderNo);

    int updateOrderAmtAndQty(Tbmqq440Vo tbmqq440Vo);

    List<Tbmqq440Vo> getOrderByApproveCode(String approveCode);

    List<KeyValue> getSupplierKV(Map<String, Object> mapParam);

    List<KeyValue> getLineKV(Map<String, Object> mapParam);

    List<KeyValue> getIsTimeOutOrderKV(Map<String, Object> mapParam);

    List<PurchaseOrderDetailExportVo> getOrderDetaiExportlList (Map<String, Object> map);

    int updateOrderStatus(Map param);

    List<Tbmqq440Vo> selectNeedWarnOrder(@Param("warnTimes") List<Integer> warnTimes);

    List<Tbmqq440Vo> selectNotReceiveOrderBySupplierNo(@Param("needWarnSuppliers") List<String> needWarnSuppliers);
}