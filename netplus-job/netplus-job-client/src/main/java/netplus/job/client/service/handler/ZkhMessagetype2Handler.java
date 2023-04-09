package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.joint.zkh.api.rest.ZkhOutRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZkhMessagetype2Handler {

    @Autowired
    private ZkhOutRest zkhOutRest;

//    @Override
//    public ReturnT<String> execute(String param) throws Exception {
//
//        XxlJobLogger.log("ZkhMessagetype2Handler job start");
//
//        zkhOutRest.goodPriceSync();
//
//        return SUCCESS;
//    }

    @XxlJob(value="ZkhMessagetype2Handler")
    public void execute() throws Exception {
        log.info("ZkhMessagetype2Handler job start");

        zkhOutRest.goodPriceSync();

        log.info("ZkhMessagetype2Handler job end");
    }
}
