package netplus.joint.zkh.out.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.zkh.api.pojo.GoodsDetailVo;
import netplus.joint.zkh.api.response.out.MessageResponse;
import netplus.joint.zkh.api.response.out.message.MessageType16ResultVo;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq432;
import netplus.mall.api.request.good.UpdateZkhGoodDetailRequest;
import netplus.mall.api.rest.GoodsRest;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.NowDate;
import netplus.utils.json.JsonUtil;
import netplus.utils.number.NumberUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//商品详情同步
@Service
public class ZkhGoodDetailSyncBiz {

    private static Log logger = LogFactory.getLog(ZkhGoodDetailSyncBiz.class);

    public static final Gson gson = JsonUtil.JsonInstance;

    @Autowired
    private ZkhMessageBiz zkhMessageBiz;

    @Autowired
    private ZkhOutBiz zkhOutBiz;

    @Autowired
    private GoodsRest goodsRest;

    @Autowired
    private ServiceConfigRest serviceConfigRest;


    public void goodDetailSync () {

        int success = 0;
        int error = 0;

        NowDate nowDate = new NowDate();

        List<MessageResponse> msgRespList = zkhMessageBiz.getMessageType(16);
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        String supplierNo = tbmqq461.getVal();
        Map<String, String> skuAndMsgMap = new HashMap();

        for (MessageResponse<MessageType16ResultVo> msgResp: msgRespList) {

            MessageType16ResultVo messageType16ResultVo = gson.fromJson(gson.toJson(msgResp.getResult()), MessageType16ResultVo.class);
            skuAndMsgMap.put(messageType16ResultVo.getSkuId(), msgResp.getId());

        }

        if (skuAndMsgMap.keySet().size() > 0) {

            for (String skuId: skuAndMsgMap.keySet()) {
                GoodsDetailVo goodsDetail = zkhOutBiz.getGoodsDetails(skuId);

                Tbmqq430 tbmqq430 = new Tbmqq430();
                tbmqq430.setGoodno(skuId);
                tbmqq430.setSupplierno(supplierNo);
                tbmqq430.setMinbuyqty(NumberUtil.i2b(goodsDetail.getMoq()));
                tbmqq430.setBrand(goodsDetail.getBrandName());
                tbmqq430.setUpdateuser(SysCodeEnum.ZKH.code);
                tbmqq430.setUpdatedate(nowDate.getDateStr());
                tbmqq430.setUpdatetime(nowDate.getTimeStr());

                Tbmqq432 tbmqq432 = new Tbmqq432();
                tbmqq432.setContent(StringUtils.isEmpty(goodsDetail.getIntroduction()) ? " ": goodsDetail.getIntroduction());
                tbmqq432.setUpdateuser(SysCodeEnum.ZKH.code);
                tbmqq432.setUpdatedate(nowDate.getDateStr());
                tbmqq432.setUpdatetime(nowDate.getTimeStr());

                tbmqq432.setCreateuser(SysCodeEnum.ZKH.code);
                tbmqq432.setCreatedate(nowDate.getDateStr());
                tbmqq432.setCreatetime(nowDate.getTimeStr());


                UpdateZkhGoodDetailRequest updateZkhGoodDetailRequest = new UpdateZkhGoodDetailRequest();
                updateZkhGoodDetailRequest.setTbmqq430(tbmqq430);
                updateZkhGoodDetailRequest.setTbmqq432(tbmqq432);

                int updateCount = goodsRest.updateZkhGoodDetail(updateZkhGoodDetailRequest);
                if (updateCount == 1) {
                    zkhMessageBiz.deleteMessage(skuAndMsgMap.get(skuId));
                    success ++;
                }else{
                    error ++;
                }
            }
        }

        logger.info(String.format("震坤行商品介绍变更消息处理: 成功：%d, 失败：%d, 总数：%d", success, error, success+error));
    }
}
