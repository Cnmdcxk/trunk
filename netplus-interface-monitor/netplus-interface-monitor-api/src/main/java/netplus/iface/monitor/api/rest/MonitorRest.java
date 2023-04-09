package netplus.iface.monitor.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.iface.monitor.api.Urls;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "netplus-interface-monitor-service",
        url = "${service.netplus-interface-monitor-service}",
        contextId = "MonitorRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)

public interface MonitorRest {
    @PostMapping(Urls.Interfaces.deleteInterFaceInformation)
    int deleteInterFaceInformation();
}
