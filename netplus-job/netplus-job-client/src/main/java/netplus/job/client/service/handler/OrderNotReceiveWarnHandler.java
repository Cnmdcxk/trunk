package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.mall.api.rest.OrderRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderNotReceiveWarnHandler {

    @Autowired
    private OrderRest orderRest;

    @XxlJob(value="OrderNotReceiveWarnHandler")
    public void execute() throws Exception {

        log.info("OrderNotReceiveWarnHandler job start");

        orderRest.orderNotReceiveWarn();

        log.info("OrderNotReceiveWarnHandler job end");
    }
}
