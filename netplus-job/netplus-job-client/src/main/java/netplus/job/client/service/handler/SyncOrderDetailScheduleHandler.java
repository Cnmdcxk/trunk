package netplus.job.client.service.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import netplus.mall.api.request.order.SyncOrderDetailScheduleRequest;
import netplus.mall.api.rest.OrderRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncOrderDetailScheduleHandler {

    @Autowired
    private OrderRest orderRest;

    @XxlJob(value="SyncOrderDetailScheduleHandler")
    public void execute() throws Exception {

        XxlJobHelper.log("SyncOrderDetailScheduleHandler job start");

        SyncOrderDetailScheduleRequest syncOrderDetailScheduleRequest = new SyncOrderDetailScheduleRequest();
        orderRest.syncOrderDetailSchedule(syncOrderDetailScheduleRequest);

        XxlJobHelper.log("SyncOrderDetailScheduleHandler job end");
    }
}
