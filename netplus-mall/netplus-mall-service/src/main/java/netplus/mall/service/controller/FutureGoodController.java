package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.mall.api.Urls;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.rest.FutureGoodRest;
import netplus.mall.service.biz.FutureGoodBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FutureGoodController extends BasedController implements FutureGoodRest {

    @Autowired
    private FutureGoodBiz futureGoodBiz;

    @Override
    @Anonymous
    @PostMapping(Urls.FutureGood.batchImport)
    public void batchImport(@RequestBody List<Tbmqq430> list){
        futureGoodBiz.batchImport(list);
    }
}
