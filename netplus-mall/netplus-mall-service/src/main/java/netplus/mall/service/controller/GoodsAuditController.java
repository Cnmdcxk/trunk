package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.mall.api.Urls;
import netplus.mall.api.request.good.BatchAuditRequest;
import netplus.mall.service.biz.GoodsAuditBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 商品审核
 * @author: yxw
 * @date: 2021/6/28 15:21
 */
@RestController
public class GoodsAuditController extends BasedController {

    @Autowired
    private GoodsAuditBiz goodsAuditBiz;

    @PostMapping(Urls.GoodsAudit.batchAudit)
    public void batchAudit(@RequestBody BatchAuditRequest request) {
        goodsAuditBiz.batchAudit(request);
    }

}
