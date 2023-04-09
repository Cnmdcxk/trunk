package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.provider.api.rest.ProvideRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SyncYgUserHandler {

    @Autowired
    private ProvideRest provideRest;

    @XxlJob(value="SyncYgUserHandler")
    public void execute() throws Exception {

        log.info("SyncYgUserHandler job start");

        provideRest.getYgUser();

        log.info("SyncYgUserHandler job end");
    }

}
