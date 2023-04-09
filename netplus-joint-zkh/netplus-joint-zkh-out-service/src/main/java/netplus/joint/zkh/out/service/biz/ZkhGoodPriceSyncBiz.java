package netplus.joint.zkh.out.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.zkh.api.pojo.GoodsPriceVo;
import netplus.joint.zkh.api.response.out.MessageResponse;
import netplus.joint.zkh.api.response.out.message.MessageType2ResultVo;
import netplus.mall.api.request.good.UpdateZkhGoodPriceRequest;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//商品价格同步
@Service
public class ZkhGoodPriceSyncBiz {

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



    public void goodPriceSync () {

        int success = 0;
        int error = 0;
        NowDate nowDate = new NowDate();

        List<MessageResponse> msgRespList = zkhMessageBiz.getMessageType(2);
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        String supplierNo = tbmqq461.getVal();
        Map<String, String> skuAndMsgMap = new HashMap();


        for (MessageResponse msgResp: msgRespList) {

            MessageType2ResultVo messageType2ResultVo = gson.fromJson(gson.toJson(msgResp.getResult()), MessageType2ResultVo.class);
            skuAndMsgMap.put(messageType2ResultVo.getSkuId(), msgResp.getId());

        }

        if (skuAndMsgMap.keySet().size() > 0) {

            List<GoodsPriceVo> goodsPriceVoList = zkhOutBiz.getSellPrice(new ArrayList(skuAndMsgMap.keySet()));

            for (GoodsPriceVo g: goodsPriceVoList) {

                UpdateZkhGoodPriceRequest updateZkhGoodPriceRequest = new UpdateZkhGoodPriceRequest();
                updateZkhGoodPriceRequest.setGoodNo(g.getSkuId());
                updateZkhGoodPriceRequest.setPrice(NumberUtil.d2bPrice(g.getPrice()));
                updateZkhGoodPriceRequest.setNoTaxPrice(NumberUtil.d2bPrice(g.getNakedPrice()));
                updateZkhGoodPriceRequest.setSupplierNo(supplierNo);
                updateZkhGoodPriceRequest.setUpdateUser(SysCodeEnum.ZKH.code);
                updateZkhGoodPriceRequest.setUpdateDate(nowDate.getDateStr());
                updateZkhGoodPriceRequest.setUpdateTime(nowDate.getTimeStr());

                int updateCount = goodsRest.updateZkhGoodPrice(updateZkhGoodPriceRequest);
                if (updateCount == 1) {
                    zkhMessageBiz.deleteMessage(skuAndMsgMap.get(g.getSkuId()));
                    success ++;
                }else{
                    error ++;
                }
            }
        }

        logger.info(String.format("震坤行价格变更消息处理: 成功：%d, 失败：%d, 总数：%d", success, error, success+error));
    }
}
