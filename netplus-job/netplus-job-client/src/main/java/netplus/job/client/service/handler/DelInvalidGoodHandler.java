package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.mall.api.rest.GoodsRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DelInvalidGoodHandler {

    @Autowired
    private GoodsRest goodsRest;

    @XxlJob(value="DelInvalidGoodHandler")
    public void execute() throws Exception {

        log.info("DelInvalidGoodHandler job start");

        int count = goodsRest.delInvalidGood();

        log.info("DelInvalidGoodHandler job end");
    }
}
