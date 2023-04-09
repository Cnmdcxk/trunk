package netplus.mall.api.rest;

import netplus.component.entity.response.ApiResponse;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
//        url = "http://127.0.0.1:20037",
        contextId = "ShoppingCartRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ShoppingCartRest {

    @PostMapping(Urls.ShoppingCart.erpAddShoppingCart)
    ApiResponse erpAddShoppingCart(@RequestBody ErpAddShoppingCartRequest request);
}
