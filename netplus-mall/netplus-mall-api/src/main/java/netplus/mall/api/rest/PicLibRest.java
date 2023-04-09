package netplus.mall.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.request.picLib.GetPicByMatrlTmAndSupplierNoRequest;
import netplus.mall.api.vo.Tbmqq407Vo;
import netplus.mall.api.vo.picLib.Tbmqq429Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "PicLibRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface PicLibRest {

    @PostMapping(Urls.PicLib.getPicByMatrlTmAndSupplierNo)
    List<Tbmqq429Vo> getPicByMatrlTmAndSupplierNo(@RequestBody GetPicByMatrlTmAndSupplierNoRequest request);


    @PostMapping(Urls.PicLib.getPicByMatrlTm)
    List<Tbmqq407Vo> getPicByMatrlTm(@RequestBody List<String> request);
}
