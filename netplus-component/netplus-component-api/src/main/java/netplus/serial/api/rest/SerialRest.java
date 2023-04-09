package netplus.serial.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.serial.api.request.SerialParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * serial相关接口
 */
@FeignClient(
        value = "netplus-component-service",
        url = "${service.netplus-component-service}",
        contextId = "SerialRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class})
public interface SerialRest {

    /**
     * core
     */
    @PostMapping(SerialConstants.core)
    String core(SerialParam serialParam);

    /**
     * get
     */
    @PostMapping(SerialConstants.get)
    String get(String prefix);


    @PostMapping(SerialConstants.getRg)
    String getRg(@RequestBody String prefix);
}
