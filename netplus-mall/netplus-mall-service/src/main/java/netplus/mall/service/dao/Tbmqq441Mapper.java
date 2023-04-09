package netplus.mall.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netplus.component.entity.data.KeyValue;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441Key;
import netplus.mall.api.vo.order.AgentInfoVo;
import netplus.mall.api.vo.order.AgentWarnVo;
import netplus.mall.api.vo.order.PurchaseOrderDetailExportVo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Tbmqq441Mapper extends BaseMapper<Tbmqq441> {
    int deleteByPrimaryKey(Tbmqq441Key key);

    int insert(Tbmqq441 record);

    int insertSelective(Tbmqq441 record);

    Tbmqq441 selectByPrimaryKey(Tbmqq441Key key);

    int updateByPrimaryKeySelective(Tbmqq441 record);

    int updateByPrimaryKey(Tbmqq441 record);

    void batchInsert(List<Tbmqq441Vo> list);

    List<Tbmqq441Vo> getOrderDetail (Map<String, Object> map);

    List<Tbmqq441Vo> getOrderDetailList (Map<String, Object> map);

    int getOrderDetailCount(Map<String, Object> mapParam);

    int changeOrderDetailStatus(Map<String, Object> map);

    List<Tbmqq441Vo> getApproveDetail(String approveCode);

    List<Tbmqq441Vo> getSyncScheduleOrderDetailId(Map<String, Object> map);

    List<Tbmqq441Vo> getSyncInvoiceOrderDetailId(Map<String, Object> map);

    int updateQtyByOrderDetailId (Tbmqq441Vo tbmqq441Vo);

    List<PurchaseOrderDetailExportVo> getOrderDetaiExportlList (Map<String, Object> map);

    List<String> getUsedDeviceApply (@Param("deviceApplyNoList") List<String> deviceApplyNoList);

    int updateLeadDate(String orderNo);

    int updateOrderDetailCancelStatus(String orderDetailId);

    Tbmqq441Vo getOrderNoById(String orderDetailId);

    List<Tbmqq441Vo> getNoCheckMatrlIdQty();

    int updateInvoiceQtyByOrderDetailId(Tbmqq441Vo tbmqq441Vo);

    int checkOrderIsCancel(String orderNo);

    int checkOrderIsOver(String orderNo);

    List<KeyValue> getMallInventoryByMatrlId(@Param("matrlIdList") List<String> matrlIdList, @Param("statusList") List<String> statusList);

    List<Tbmqq441Vo> getMallGoodsInventoryDetailByMatrlId(@Param("matrlIdList") List<String> matrlIdList, @Param("statusList") List<String> statusList, @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    int getMallGoodsInventoryDetailCountByMatrlId(@Param("matrlIdList") List<String> matrlIdList, @Param("statusList") List<String> statusList);

    List<Tbmqq441Vo> getOrderDetailByOrderNoList(@Param("orderNoList") List<String> orderNoList);

    void updateOrderDetailStatus(Map param);

    List<AgentInfoVo> getAgentInfoByOrderNo(@Param("needWarnOrderNo") List<String> needWarnOrderNo);

    List<AgentWarnVo> getWarnOrderInfoByAgent(@Param("agentNoList") List<String> agentNoList);


    List<Tbmqq441Vo> getCountGoodInfo(Map<String, Object> map);

    int getCountGoodCount(Map<String, Object> map);
}