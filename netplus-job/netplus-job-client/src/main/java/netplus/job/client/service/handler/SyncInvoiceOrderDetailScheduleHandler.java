package netplus.job.client.service.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import netplus.mall.api.request.order.SyncOrderDetailScheduleRequest;
import netplus.mall.api.rest.OrderRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncInvoiceOrderDetailScheduleHandler {

    @Autowired
    private OrderRest orderRest;

    @XxlJob(value="SyncInvoiceOrderDetailScheduleHandler")
    public void execute() throws Exception {

        XxlJobHelper.log("SyncInvoiceOrderDetailScheduleHandler job start");

        SyncOrderDetailScheduleRequest syncOrderDetailScheduleRequest = new SyncOrderDetailScheduleRequest();
        orderRest.syncInvoiceOrderDetailSchedule(syncOrderDetailScheduleRequest);

        XxlJobHelper.log("SyncInvoiceOrderDetailScheduleHandler job end");
    }
}
