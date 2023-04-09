package netplus.mall.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.request.good.*;
import netplus.mall.api.vo.GoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "GoodsRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface GoodsRest {

    @PostMapping(Urls.GoodsListed.batchInsert)
    void batchInsert(@RequestBody BatchInsertGoodRequest request);

    @PostMapping(Urls.GoodsListed.updateZkhGoodPrice)
    int updateZkhGoodPrice(@RequestBody UpdateZkhGoodPriceRequest request);

    @PostMapping(Urls.GoodsListed.updateZkhGoodDetail)
    int updateZkhGoodDetail(@RequestBody UpdateZkhGoodDetailRequest request);

    @PostMapping(Urls.GoodsListed.updateGoodInvalid)
    int updateGoodInvalid();

    @PostMapping(Urls.GoodsListed.SetGoodRank)
    int  updateGoodRank();

    @PostMapping(Urls.GoodsListed.getGoodBySupplierNo)
    List<GoodsVo> getGoodBySupplierNo(@RequestBody GetGoodBySupplierNoRequest request);

    @PostMapping(Urls.GoodsListed.updateZkhPic)
    int updateZkhPic(@RequestBody UpdateZkhPicRequest request);


    @PostMapping(Urls.GoodsListed.updateZkhPriceAndDetail)
    int updateZkhPriceAndDetail (@RequestBody UpdateZkhPriceAndDetailRequest request);


    @PostMapping(Urls.GoodsListed.updateZkhGoodQty)
    int updateZkhGoodQty(@RequestBody UpdateZkhGoodQtyRequest request);

    @PostMapping(Urls.GoodsListed.delInvalidGood)
    int delInvalidGood();

}
