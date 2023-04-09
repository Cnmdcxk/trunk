package netplus.cache.service.controller;


import io.swagger.annotations.Api;
import netplus.cache.api.CacheConstants;
import netplus.cache.api.rest.CacheRest;
import netplus.cache.service.biz.RedisBiz;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.AccessAnonymous;
import netplus.component.entity.auth.Anonymous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags="缓存接口文档")
@RestController
public class CacheController extends BasedController implements CacheRest {

    private static Logger logger = LoggerFactory.getLogger(CacheController.class); // 日志记录

    @Autowired
    private RedisBiz redisBiz;

    @Anonymous
    @PostMapping(CacheConstants.get)
    public String get(@RequestParam("key") String key) {

        logger.info(String.format("key: %s", key));
        String newKey = wrapKey(key);

        logger.info(String.format("get newKey: %s", newKey));

        String value = redisBiz.get(newKey);

        logger.info(String.format("get newKey: %s", value));

        return value;
    }

    @Anonymous
    @PostMapping(CacheConstants.set)
    public boolean set(@RequestParam("key") String key,
                       @RequestParam("value") String value,
                       @RequestParam("expire") Long expire) {

        logger.info(String.format("set: %s, %s", key, value));

        String newKey = wrapKey(key);

        logger.info(String.format("set newKey: %s, value: %s", key, value));
        return redisBiz.set(newKey, value, expire);
    }

    @Anonymous
    @PostMapping(CacheConstants.remove)
    public boolean remove(@RequestParam("key") String key) {
        String newKey = wrapKey(key);
        return redisBiz.remove(newKey);
    }

    @Anonymous
    @PostMapping(CacheConstants.exists)
    public boolean exists(@RequestParam("key") String key) {
        String newKey = wrapKey(key);
        return redisBiz.exists(newKey);
    }


    @Anonymous
    @PostMapping(CacheConstants.incr)
    public Long incr(@RequestParam("key") String key) {
        String newKey = wrapKey(key);
        return redisBiz.incr(newKey);
    }

    @Anonymous
    @PostMapping(CacheConstants.ttl)
    public Long ttl(@RequestParam("key") String key) {
        String newKey = wrapKey(key);
        return redisBiz.ttl(newKey);
    }

    @Anonymous
    @PostMapping(CacheConstants.rpush)
    public Long rpush(@RequestParam("key") String key, @RequestParam("value") String value) {
        String newKey = wrapKey(key);
        return redisBiz.rpush(newKey, value);
    }

    @Anonymous
    @PostMapping(CacheConstants.lpop)
    public String lpop(@RequestParam("key") String key) {
        String newKey = wrapKey(key);
        return redisBiz.lpop(newKey);
    }

    @Anonymous
    @AccessAnonymous
    @PostMapping(CacheConstants.getVer)
    public Float getVer(){
        return redisBiz.getVer();
    }

    private String wrapKey(String key){
        String appId = getAppID();
        return String.format("%s_%s", appId, key);
    }
}
