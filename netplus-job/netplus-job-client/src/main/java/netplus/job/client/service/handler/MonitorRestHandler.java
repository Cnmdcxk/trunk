package netplus.job.client.service.handler;

import com.google.gson.Gson;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.iface.monitor.api.rest.MonitorRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MonitorRestHandler  {
    @Autowired
    private MonitorRest monitorRest;

    @XxlJob(value="MonitorRestHandler")
    public void execute() throws Exception {

        log.info("MonitorRestHandler job start");

        int count = monitorRest.deleteInterFaceInformation();

        log.info(String.format("MonitorRestHandler job end: %s", new Gson().toJson(count)));

    }
}
