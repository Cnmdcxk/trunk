package netplus.cache.service.biz;

import netplus.component.entity.version.IVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisBiz implements IVersion {

    private static Logger logger = LoggerFactory.getLogger(RedisBiz.class);


    private static final Long defaultExpire =  60 * 60L;

    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    public String get(final String key){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return (String) operations.get(key);
    }

    @SuppressWarnings("unchecked")
    public boolean set(final String key, String value){
        try{
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime){
        try {
            expireTime = expireTime == null ? defaultExpire : expireTime;

            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean remove(final String key){
        if(exists(key))
            return redisTemplate.delete(key);
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    @SuppressWarnings("unchecked")
    public Long incr(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key);
    }


    @SuppressWarnings("unchecked")
    public Long ttl(final String key) {
        return redisTemplate.getExpire(key);
    }

    @SuppressWarnings("unchecked")
    public Long rpush(final String key, final String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @SuppressWarnings("unchecked")
    public String lpop(final String key) {
        Object result = redisTemplate.opsForList().leftPop(key);
        if (null == result) {
            return null;
        }
        return (String) result;
    }


    public Float getVer(){
        String key = "static_ver";
        String value = this.get(key);
        if (StringUtils.isEmpty(value)){
            value = String.valueOf(new Date().getTime());
            this.set(key, value);
        }


        return Float.valueOf(value);
    }

    public RedisTemplate getRedisTemplate(){
        return redisTemplate;
    }
}
