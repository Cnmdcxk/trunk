package netplus.serial.service.biz;

import netplus.cache.api.rest.CacheRest;
import netplus.serial.api.enums.SqFormat;
import netplus.serial.api.pojo.db.Tbmqq462;
import netplus.serial.api.request.SerialParam;
import netplus.serial.service.dao.Tbmqq462Mapper;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 序列码逻辑
 */
@Service
public class SerialBiz {

    private static Log logger = LogFactory.getLog(SerialBiz.class);

    @Autowired(required = false)
    private Tbmqq462Mapper tbmqq462Mapper;

    @Autowired
    private CacheRest cacheRest;

    /**
     * 根据appid， code 获取对应的序列
     *
     * @param code  代码
     * @param appId 应用ID
     * @return 序列
     */
    public Integer serialIncr(String code, String appId, Integer startSq) {

        Long sq = cacheRest.incr(code);

        return sq.intValue();
    }

    public String getSerial(SerialParam serialParam) {
        if (ObjectUtil.isEmpty(serialParam.getStartSq())) {
            serialParam.setStartSq(1);
        }

        SqFormat sqFormat = SqFormat.Of(serialParam.getSqFormat());
        String result_key = sqFormat.getCodeFn.apply(serialParam.getPrefix());


        // appId 流水号
        Integer sq = serialIncr(result_key, serialParam.getAppId(), serialParam.getStartSq());

        String result = result_key + sqFormat.sqToString.apply(sq);

        // 记录流水号日志

        new Thread(() -> {
            NowDate now = new NowDate();
            Tbmqq462 tbmqq462 = new Tbmqq462();
            tbmqq462.setCode(result);
            tbmqq462.setAppid(serialParam.getAppId());
            tbmqq462.setKey(result_key);
            tbmqq462.setCreatedate(now.getDateStr());
            tbmqq462.setCreatetime(now.getTimeStr());
            tbmqq462Mapper.insertSelective(tbmqq462);
        }).start();

        return result;
    }


    public String getRg(SerialParam serialParam) {
        if (ObjectUtil.isEmpty(serialParam.getStartSq())) {
            serialParam.setStartSq(1);
        }

        SqFormat sqFormat = SqFormat.Of(serialParam.getSqFormat());

        // appId 流水号
        Integer sq = serialIncr(serialParam.getPrefix(), serialParam.getAppId(), serialParam.getStartSq());
        String result = serialParam.getPrefix() + sqFormat.sqToString.apply(sq);

        // 记录流水号日志

        new Thread(() -> {
            NowDate now = new NowDate();
            Tbmqq462 tbmqq462 = new Tbmqq462();
            tbmqq462.setCode(result);
            tbmqq462.setAppid(serialParam.getAppId());
            tbmqq462.setKey(serialParam.getPrefix());
            tbmqq462.setCreatedate(now.getDateStr());
            tbmqq462.setCreatetime(now.getTimeStr());
            tbmqq462Mapper.insertSelective(tbmqq462);
        }).start();

        return result;
    }
}
