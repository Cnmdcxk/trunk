package netplus.joint.zkh.out.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.zkh.api.pojo.GoodsDetailVo;
import netplus.joint.zkh.api.pojo.GoodsPriceVo;
import netplus.joint.zkh.api.response.out.MessageResponse;
import netplus.joint.zkh.api.response.out.message.MessageType6ResultVo;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq432;
import netplus.mall.api.request.good.GetGoodBySupplierNoRequest;
import netplus.mall.api.request.good.UpdateZkhPriceAndDetailRequest;
import netplus.mall.api.rest.GoodsRest;
import netplus.mall.api.vo.GoodsVo;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.NowDate;
import netplus.utils.json.JsonUtil;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZkhGoodPriceAndDetailSyncBiz {


    private static Log logger = LogFactory.getLog(ZkhGoodPriceAndDetailSyncBiz.class);

    public static final Gson gson = JsonUtil.JsonInstance;

    @Autowired
    private ZkhMessageBiz zkhMessageBiz;

    @Autowired
    private ZkhOutBiz zkhOutBiz;

    @Autowired
    private GoodsRest goodsRest;

    @Autowired
    private ServiceConfigRest serviceConfigRest;


    public void goodPriceAndDetailSync () {

        NowDate nowDate = new NowDate();
        int success = 0;
        int error = 0;

        List<MessageResponse> msgRespList = zkhMessageBiz.getMessageType(6);
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        String supplierNo = tbmqq461.getVal();
        Map<String, String> skuAndMsgMap = new HashMap();


        GetGoodBySupplierNoRequest getGoodBySupplierNoRequest = new GetGoodBySupplierNoRequest();
        getGoodBySupplierNoRequest.setSupplierNo(supplierNo);
        List<GoodsVo> goodsVoList = goodsRest.getGoodBySupplierNo(getGoodBySupplierNoRequest);

        Map<String, String> skuAndGoodIdMap = goodsVoList.stream().collect(Collectors.toMap(GoodsVo::getGoodno, g -> g.getGoodid()));

        //查询震坤行消息池内添加，删除的商品
        for (MessageResponse msgResp: msgRespList) {

            MessageType6ResultVo messageType6ResultVo = gson.fromJson(gson.toJson(msgResp.getResult()), MessageType6ResultVo.class);
            skuAndMsgMap.put(messageType6ResultVo.getSkuId(), msgResp.getId());

            //捕捉异常，保证后面消息能继续处理
            try {

                String goodId = skuAndGoodIdMap.get(messageType6ResultVo.getSkuId());
                if (StringUtils.isEmpty(goodId)) {
                    continue;
                }

                // 删除商品状态这里不处理
                if (messageType6ResultVo.getState() != 1) {
                    continue;
                }

                String skuId = messageType6ResultVo.getSkuId();
                BigDecimal price = BigDecimal.ZERO;
                BigDecimal noTaxPrice = BigDecimal.ZERO;

                //获取价格
                List<GoodsPriceVo> goodsPriceVoList = zkhOutBiz.getSellPrice(Arrays.asList(skuId));
                if (ObjectUtil.nonEmpty(goodsPriceVoList)) {
                    price = NumberUtil.d2bPrice(goodsPriceVoList.get(0).getPrice());
                    noTaxPrice = NumberUtil.d2bPrice(goodsPriceVoList.get(0).getNakedPrice());
                }

                //获取zkh商品基本信息
                GoodsDetailVo goodsDetails = zkhOutBiz.getGoodsDetails(skuId);

                //zkh商品信息
                Tbmqq430 tbmqq430 = new Tbmqq430();
                tbmqq430.setGoodid(goodId);
                tbmqq430.setBrand(goodsDetails.getBrandName());
                tbmqq430.setPrice(price);
                tbmqq430.setNotaxprice(noTaxPrice);
                tbmqq430.setOriginprice(price);
                tbmqq430.setMinbuyqty(NumberUtil.i2b(goodsDetails.getMoq()));
                tbmqq430.setUpdatedate(nowDate.getDateStr());
                tbmqq430.setUpdatetime(nowDate.getTimeStr());
                tbmqq430.setUpdateuser(SysCodeEnum.ZKH.code);

                //获取zkh商品介绍信息
                Tbmqq432 tbmqq432 = new Tbmqq432();
                tbmqq432.setGoodid(goodId);
                tbmqq432.setContent(goodsDetails.getIntroduction());
                tbmqq432.setUpdatedate(nowDate.getDateStr());
                tbmqq432.setUpdatetime(nowDate.getTimeStr());
                tbmqq432.setUpdateuser(SysCodeEnum.ZKH.code);


                UpdateZkhPriceAndDetailRequest updateZkhPriceAndDetailRequest = new UpdateZkhPriceAndDetailRequest();
                updateZkhPriceAndDetailRequest.setTbmqq430(tbmqq430);
                updateZkhPriceAndDetailRequest.setTbmqq432(tbmqq432);
                int updateCount = goodsRest.updateZkhPriceAndDetail(updateZkhPriceAndDetailRequest);
                if (updateCount == 1) {
                    zkhMessageBiz.deleteMessage(msgResp.getId());
                    success ++;
                }else{
                    error ++;
                }

                logger.info(String.format(
                        "消息id:%s, sku: %s, 商品同步成功",
                        msgResp.getId(),
                        messageType6ResultVo.getSkuId()
                        )
                );

            } catch (Exception e) {


                logger.info(String.format(
                        "消息id:%s, sku: %s, 商品同步失败, 原因：%s",
                        msgResp.getId(),
                        messageType6ResultVo.getSkuId(),
                        org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e))
                );

            }
        }

        logger.info(String.format("震坤行新增商品消息处理:总数：%d, 成功：%d, 失败：%d", msgRespList.size(), success, msgRespList.size() - success));


    }
}
