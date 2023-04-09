package netplus.mall.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.request.basicData.GetMaterialInfoRequest;
import netplus.mall.api.request.basicData.GetMatrlByIdsAndSupplierNoRequest;
import netplus.mall.api.request.basicData.GetMatrlNoRequest;
import netplus.mall.api.vo.CategoryVo;
import netplus.mall.api.vo.Tbmqq405Vo;
import netplus.mall.api.vo.Tbmqq406Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "BasicDataRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface BasicDataRest {

    @PostMapping(Urls.BasicData.getCategoryByGoodsno)
    CategoryVo getCategoryByGoodsno(@RequestBody String skuId);

    @PostMapping(Urls.BasicData.getAllMatrlList)
    List<Tbmqq405Vo> getAllMatrlList();

    @PostMapping(Urls.BasicData.getMaterialInfo)
    List<Tbmqq405Vo> getMaterialInfo(@RequestBody GetMaterialInfoRequest request);

    @PostMapping(Urls.BasicData.getMatrlNoByGoodNo)
    Tbmqq406Vo getMatrlNoByGoodNo (@RequestBody GetMatrlNoRequest request);

    @PostMapping(Urls.BasicData.syncBasicData)
    void syncBasicData();

    @PostMapping(Urls.BasicData.getMatrlByIdsAndSupplierNo)
    List<Tbmqq405Vo> getMatrlByIdsAndSupplierNo(@RequestBody GetMatrlByIdsAndSupplierNoRequest request);
}
