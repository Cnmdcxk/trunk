package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.mall.api.rest.GoodsRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoodsRankHandler {

    @Autowired
    private GoodsRest goodsRest;

    @XxlJob(value="GoodRankHandler")
    public void execute() throws Exception {

        log.info("GoodRankHandler job start");

        int count = goodsRest.updateGoodRank();

        log.info("GoodRankHandler job end");
    }
}