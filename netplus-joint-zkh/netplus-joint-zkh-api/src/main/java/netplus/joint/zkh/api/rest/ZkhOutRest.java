package netplus.joint.zkh.api.rest;


import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.joint.zkh.api.Urls;
import netplus.joint.zkh.api.pojo.GoodsPriceVo;
import netplus.joint.zkh.api.pojo.PackageVo;
import netplus.joint.zkh.api.pojo.SubmitOrderVo;
import netplus.joint.zkh.api.request.out.ServiceOrderRequest;
import netplus.joint.zkh.api.request.out.SkuThirdState;
import netplus.joint.zkh.api.request.out.SubmitOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "netplus-joint-zkh-out-service",
        url = "${service.netplus-joint-zkh-out-service}",
        contextId = "ZkhOutRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ZkhOutRest {

    @RequestMapping(Urls.Out.goodPriceSync)
    void goodPriceSync();

    @RequestMapping(Urls.Out.goodDetailSync)
    void goodDetailSync();

    @RequestMapping(Urls.Out.getMessage6)
    void getMessageType6();

    @RequestMapping(Urls.Out.getMessage4)
    void getMessageType4();

    @RequestMapping(Urls.Out.getMessage2)
    void getMessageType2();

    @RequestMapping(Urls.Out.getMessage16)
    void getMessageType16();

    @PostMapping(Urls.Out.getSellPrice)
    List<GoodsPriceVo> getSellPrice(@RequestBody List<String> skuIdList);

    @RequestMapping(Urls.Out.getGoodsThirdState)
    void getGoodsThirdState(@RequestBody List<SkuThirdState> skuThirdStates);

    @RequestMapping(Urls.Out.submitOrder)
    SubmitOrderVo submitOrder(@RequestBody SubmitOrderRequest request);

    @RequestMapping(Urls.Out.confirmOrder)
    void confirmOrder(@RequestParam("orderId") String orderId,@RequestParam("referenceNumber") String referenceNumber );

    @RequestMapping(Urls.Out.cancelOrder)
    void cancelOrder(@RequestParam("orderId") String orderId);

    @RequestMapping(Urls.Out.itemCancel)
    void itemCancel(@RequestParam("orderId") String orderId, @RequestParam("skuNoList") List<String> skuNoList);

    @RequestMapping(Urls.Out.orderCompletion)
    void orderCompletion(@RequestParam("orderId") String orderId);

    @RequestMapping(Urls.Out.serviceOrder)
    String serviceOrder(@RequestBody ServiceOrderRequest serviceOrderRequest);

    @RequestMapping(Urls.Out.orderTrack)
    PackageVo orderTrack(@RequestParam("packageId") String packageId);

    @RequestMapping(Urls.Out.picSync)
    void picSync ();

    @RequestMapping(Urls.Out.qtySync)
    void qtySync ();

    @RequestMapping(Urls.Out.goodPriceAndDetailSync)
    void goodPriceAndDetailSync ();
}
