package netplus.job.client.service.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import netplus.provider.api.rest.DepartmentRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SyncDepartmentHandler {

    @Autowired
    private DepartmentRest departmentRest;

    @XxlJob(value="SyncDepartmentHandler")
    public void execute() throws Exception {

        log.info("SyncDepartmentHandler job start");

        departmentRest.syncDepartment();

        log.info("SyncDepartmentHandler job end");
    }
}
