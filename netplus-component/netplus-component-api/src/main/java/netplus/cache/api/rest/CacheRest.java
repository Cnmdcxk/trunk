package netplus.cache.api.rest;


import netplus.cache.api.CacheConstants;
import netplus.component.entity.version.IVersion;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "netplus-component-service",
        url="${service.netplus-component-service}",
        contextId = "CacheRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface CacheRest extends IVersion {

    @PostMapping(CacheConstants.get)
    String get(@RequestParam("key") String key);

    @PostMapping(CacheConstants.set)
    boolean set(@RequestParam("key") String key,
                @RequestParam("value") String value,
                @RequestParam("expire") Long expire);

    @PostMapping(CacheConstants.remove)
    boolean remove(@RequestParam("key") String key);

    @PostMapping(CacheConstants.exists)
    boolean exists(@RequestParam("key") String key);

    @PostMapping(CacheConstants.incr)
    Long incr(@RequestParam("key") String key);

    @PostMapping(CacheConstants.ttl)
    Long ttl(@RequestParam("key") String key);

    @PostMapping(CacheConstants.rpush)
    Long rpush(@RequestParam("key") String key, @RequestParam("value") String value);

    @PostMapping(CacheConstants.lpop)
    String lpop(@RequestParam("key") String key);

    @PostMapping(CacheConstants.getVer)
    Float getVer();
}
