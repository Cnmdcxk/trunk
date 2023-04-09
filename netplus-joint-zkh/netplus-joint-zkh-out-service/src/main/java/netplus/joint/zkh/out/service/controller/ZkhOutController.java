package netplus.joint.zkh.out.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.joint.zkh.api.Urls;
import netplus.joint.zkh.api.pojo.*;
import netplus.joint.zkh.api.request.out.ServiceOrderRequest;
import netplus.joint.zkh.api.request.out.SkuThirdState;
import netplus.joint.zkh.api.request.out.SubmitOrderRequest;
import netplus.joint.zkh.api.rest.ZkhOutRest;
import netplus.joint.zkh.out.service.biz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("震坤行接口")
@RestController
public class ZkhOutController extends BasedController implements ZkhOutRest {

    @Autowired
    private ZkhOutBiz zkhOutBiz;

    @Autowired
    private ZkhPicSyncBiz zkhPicSyncBiz;

    @Autowired
    private ZkhQtySyncBiz zkhQtySyncBiz;

    @Autowired
    private ZkhMessageBiz zkhMessageBiz;

    @Autowired
    ZkhGoodDetailSyncBiz zkhGoodDetailSyncBiz;

    @Autowired
    ZkhGoodPriceSyncBiz zkhGoodPriceSyncBiz;

    @Autowired
    ZkhGoodPriceAndDetailSyncBiz zkhGoodPriceAndDetailSyncBiz;

    @Anonymous
    @RequestMapping(Urls.Out.goodPriceSync)
    public void goodPriceSync(){
        zkhGoodPriceSyncBiz.goodPriceSync();
    }


    @Anonymous
    @RequestMapping(Urls.Out.goodDetailSync)
    public void goodDetailSync(){
        zkhGoodDetailSyncBiz.goodDetailSync();
    }

    @Anonymous
    @RequestMapping(Urls.Out.getMessage6)
    public void getMessageType6() {
        zkhMessageBiz.getMessageType(6);
    }

    /*type=4 代表商品上下架变更消息*/
    @Anonymous
    @RequestMapping(Urls.Out.getMessage4)
    public void getMessageType4() {
        zkhMessageBiz.getMessageType(4);
    }

    /*type=2代表商品价格变更*/
    @Anonymous
    @RequestMapping(Urls.Out.getMessage2)
    public void getMessageType2() {
        zkhMessageBiz.getMessageType(2);
    }

    /*type=16 代表商品介绍及规格参数变更消息*/
    @Anonymous
    @RequestMapping(Urls.Out.getMessage16)
    public void getMessageType16() {
        zkhMessageBiz.getMessageType(16);
    }

    @Anonymous
    @PostMapping(Urls.Out.getSellPrice)
    public List<GoodsPriceVo> getSellPrice(@RequestBody List<String> skuIdList) {
        return zkhOutBiz.getSellPrice(skuIdList);
    }

    //批量查询商品库存
    @Anonymous
    @RequestMapping(Urls.Out.getProductInventory)
    public List<GoodsInventoryVo> getProductInventory(@RequestBody List<String> skuId) {
        return zkhOutBiz.getProductInventory(skuId);
    }

    //商品状态反向同步
    @Anonymous
    @RequestMapping(Urls.Out.getGoodsThirdState)
    public void getGoodsThirdState(@RequestBody List<SkuThirdState> skuThirdStates) {
        zkhOutBiz.getGoodsThirdState(skuThirdStates);
    }

    //统一下单
    @Anonymous
    @RequestMapping(Urls.Out.submitOrder)
    public SubmitOrderVo submitOrder(@RequestBody SubmitOrderRequest request) {
        return zkhOutBiz.submitOrder(request);
    }

    //确认下单
    @Anonymous
    @RequestMapping(Urls.Out.confirmOrder)
    public void confirmOrder(@RequestParam("orderId") String orderId, @RequestParam("referenceNumber") String referenceNumber) {
        zkhOutBiz.confirmOrder(orderId, referenceNumber);
    }

    //取消订单
    @Anonymous
    @RequestMapping(Urls.Out.cancelOrder)
    public void cancelOrder(@RequestParam("orderId") String orderId) {
        zkhOutBiz.cancelOrder(orderId);
    }

    //取消订单部分明细
    @Anonymous
    @RequestMapping(Urls.Out.itemCancel)
    public void itemCancel(@RequestParam("orderId") String orderId, @RequestParam("skuNoList") List<String> skuNoList) {
        zkhOutBiz.itemCancel(orderId, skuNoList);
    }

    //取消订单部分明细
    @Anonymous
    @RequestMapping(Urls.Out.orderCompletion)
    public void orderCompletion(@RequestParam("orderId") String orderId) {
        zkhOutBiz.orderCompletion(orderId);
    }

    //售后单申请
    @Anonymous
    @PostMapping(Urls.Out.serviceOrder)
    public String serviceOrder(@RequestBody ServiceOrderRequest serviceOrderRequest) {
        return zkhOutBiz.serviceOrder(serviceOrderRequest);
    }

    //查询发货单物流轨迹信息接⼝
    @Anonymous
    @RequestMapping(Urls.Out.orderTrack)
    public PackageVo orderTrack(@RequestParam("packageId") String packageId) {
        return zkhOutBiz.orderTrack(packageId);
    }


    @Anonymous
    @RequestMapping(Urls.Out.getGoodsDetails)
    public GoodsDetailVo getGoodsDetails (@RequestBody  List<String> skuId) {
        return zkhOutBiz.getGoodsDetails(skuId.get(0));
    }

    @Anonymous
    @RequestMapping(Urls.Out.getGoodsImage)
    public List<GoodsImageVo> getGoodsImage (@RequestBody  List<String> skuId) {
        return zkhOutBiz.getGoodsImage(skuId);
    }

    @Anonymous
    @RequestMapping(Urls.Out.picSync)
    public void picSync () {
        zkhPicSyncBiz.picSync();
    }


    @Anonymous
    @RequestMapping(Urls.Out.qtySync)
    public void qtySync () {
        zkhQtySyncBiz.qtySync();
    }


    @Anonymous
    @RequestMapping(Urls.Out.goodPriceAndDetailSync)
    public void goodPriceAndDetailSync () {
        zkhGoodPriceAndDetailSyncBiz.goodPriceAndDetailSync();
    }
}
