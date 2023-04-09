package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.mall.api.rest.BasicDataRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SyncBasicDataHandler {

    @Autowired
    private BasicDataRest basicDataRest;

    @XxlJob(value="SyncBasicDataHandler")
    public void execute() throws Exception {

        log.info("SyncBasicDataHandler job start");

        basicDataRest.syncBasicData();

        log.info("SyncBasicDataHandler job end");
    }
}
