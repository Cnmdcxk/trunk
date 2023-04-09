package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.joint.zkh.api.rest.ZkhOutRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZkhPicSyncHandler {

    @Autowired
    private ZkhOutRest zkhOutRest;

    @XxlJob(value="ZkhPicSyncHandler")
    public void execute() throws Exception {

        log.info("ZkhPicSyncHandler job start");

        zkhOutRest.picSync();

        log.info("ZkhPicSyncHandler job end");
    }
}
