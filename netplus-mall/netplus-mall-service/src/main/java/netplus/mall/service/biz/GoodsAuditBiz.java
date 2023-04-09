package netplus.mall.service.biz;

import netplus.component.entity.enums.MsgId;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.request.good.BatchAuditRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq436Mapper;
import netplus.messaging.api.rest.MessagingRest;
import netplus.provider.api.rest.CommonRest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class GoodsAuditBiz {

    @Resource
    private Tbmqq430Mapper tbmqq430Mapper;

    @Resource
    private IdentityRest identityRest;

    @Autowired
    private Tbmqq436Mapper tbmqq436Mapper;

    @Autowired
    private MessagingRest messagingRest;

    @Autowired
    private CommonRest commonRest;

    @Transactional(rollbackFor = Exception.class)
    public void batchAudit (BatchAuditRequest request) {

        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        String status = "";
        String oldStatus = "";
        NowDate nowDate = new NowDate();
        MsgId msgId;

//        if ("A".equals(request.getAuditRole()) && "A".equals(request.getBiz())) {
//            if (request.getRejectOrAgree().equals("Y")) {
//                status = GoodsStatusEnum.CGSHTG.getCode();
//                msgId = MsgId.GoodAudit004;
//            }else{
//                status = GoodsStatusEnum.CGSHBH.getCode();
//                msgId = MsgId.GoodAudit006;
//            }
//
//            oldStatus = GoodsStatusEnum.CGSHZ.getCode();
//        }else if ("B".equals(request.getAuditRole()) && "A".equals(request.getBiz())) {
//
//            if (request.getRejectOrAgree().equals("Y")) {
//                status = GoodsStatusEnum.YSJ.getCode();
//                msgId = MsgId.GoodAudit005;
//            }else{
//                status = GoodsStatusEnum.YBH.getCode();
//                msgId = MsgId.GoodAudit006;
//            }
//
//            oldStatus = GoodsStatusEnum.CGSHTG.getCode();
//        } else if ("A".equals(request.getAuditRole()) && "B".equals(request.getBiz())) {
//
//            if (request.getRejectOrAgree().equals("Y")) {
//                status = GoodsStatusEnum.ZYSHTG.getCode();
//                msgId = MsgId.GoodAudit001;
//            }else{
//                status = GoodsStatusEnum.ZYSHBH.getCode();
//                msgId = MsgId.GoodAudit003;
//            }
//
//            oldStatus = GoodsStatusEnum.ZYSHZ.getCode();
//
//        }else {
//
//            if (request.getRejectOrAgree().equals("Y")) {
//                status = GoodsStatusEnum.ZZSHTG.getCode();
//                msgId = MsgId.GoodAudit002;
//            }else{
//                status = GoodsStatusEnum.ZZSHBH.getCode();
//                msgId = MsgId.GoodAudit003;
//            }
//
//            oldStatus = GoodsStatusEnum.ZYSHTG.getCode();
//        }
//

        GoodsVo updateGood = new GoodsVo();
        updateGood.setGoodIdList(request.getGoodIdList());
        updateGood.setOldStatus(oldStatus);
        updateGood.setStatus(status);
        updateGood.setUpdatetime(nowDate.getTimeStr());
        updateGood.setUpdatedate(nowDate.getDateStr());
        updateGood.setUpdateuser(loginUserBean.getUserCode());

        if (request.getRejectOrAgree().equals("N")) {

            if (ObjectUtil.isEmpty(request.getRejectReason())) {
                throw new NetPlusException("驳回原因不能为空");
            }
        }

        updateGood.setRejectreason(request.getRejectReason());
        updateGood.setAudituser(loginUserBean.getUserCode());


        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());
        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("审核失败");
        }

//        afterAuditCreateMsg(request.getGoodIdList(), loginUserBean, null);
    }




}
