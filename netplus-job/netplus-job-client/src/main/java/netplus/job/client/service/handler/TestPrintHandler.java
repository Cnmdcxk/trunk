package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestPrintHandler {


    @XxlJob(value="TestPrintHandler")
    public void execute() throws Exception {

        log.info("TestPrintHandler: 1");
        log.info("TestPrintHandler: 2");
        log.info("TestPrintHandler: 3");

    }
}
