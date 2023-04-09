package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.request.mall.GetCountGoodInfoRequest;
import netplus.mall.api.request.mall.GetCountPonoInfoRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.mall.service.biz.CountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CountController extends BasedController {

    @Autowired
    private CountBiz countBiz;

    @PostMapping(Urls.Count.getCountPonoInfo)
    public PageBean<GoodsVo> getCountPonoInfo(@RequestBody GetCountPonoInfoRequest request) {
        return countBiz.getCountPonoInfo(request);
    }


    @PostMapping(Urls.Count.getCountGoodInfo)
    public PageBean<Tbmqq441Vo> getCountGoodInfo(@RequestBody GetCountGoodInfoRequest request) {
        return countBiz.getCountGoodInfo(request);
    }


    @PostMapping(Urls.Count.getPonoDetailInfo)
    public PageBean<GoodsVo> getPonoDetailInfo(@RequestBody GetCountPonoInfoRequest request) {
        return countBiz.getPonoDetailInfo(request);
    }


    @PostMapping(Urls.Count.exportCountPonoInfo)
    public String exportCountPonoInfo(@RequestBody GetCountPonoInfoRequest request) throws Exception {
        return countBiz.exportCountPonoInfo(request);
    }

    @PostMapping(Urls.Count.exportCountGoodInfo)
    public String exportCountGoodInfo(@RequestBody GetCountGoodInfoRequest request) throws Exception {
        return countBiz.exportCountGoodInfo(request);
    }

    @PostMapping(Urls.Count.exportCountPonoDetailInfo)
    public String exportCountPonoDetailInfo(@RequestBody GetCountPonoInfoRequest request) throws Exception {
        return countBiz.exportCountPonoDetailInfo(request);
    }



    @PostMapping(Urls.Count.getTabCount)
    public Map<String, Integer> getTabCount() {
        return countBiz.getTabCount();
    }




}
