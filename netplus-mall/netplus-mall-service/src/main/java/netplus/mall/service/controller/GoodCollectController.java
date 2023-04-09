package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.request.goodCollect.*;
import netplus.mall.api.vo.goodCollect.GoodCollectVo;
import netplus.mall.service.biz.GoodCollectBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodCollectController extends BasedController {

    @Autowired
    private GoodCollectBiz goodCollectBiz;

    @PostMapping(Urls.GoodsCollect.delMyCollect)
    public void delMyCollect(@RequestBody DelMyCollectRequest request) {
        goodCollectBiz.delMyCollect(request);
    }

    @PostMapping(Urls.GoodsCollect.addMyCollect)
    public void addMyCollect(@RequestBody AddMyCollectRequest request) {
        goodCollectBiz.addMyCollect(request);
    }

    @PostMapping(Urls.GoodsCollect.batchAddMyCollect)
    public void batchAddMyCollect(@RequestBody BatchAddMyCollectRequest request) {
        goodCollectBiz.batchAddMyCollect(request);
    }

    @PostMapping(Urls.GoodsCollect.getMyCollectList)
    public PageBean<GoodCollectVo> getMyCollectList (@RequestBody GetMyCollectListRequest request) {
        return goodCollectBiz.getMyCollectList(request);
    }

    @PostMapping(Urls.GoodsCollect.getMyCollectCount)
    public int getMyCollectCount(){
        return goodCollectBiz.getMyCollectCount();
    }

    @PostMapping(Urls.GoodsCollect.updateCollectRemark)
    public void updateCollectRemark(@RequestBody UpdateCollectRemarkRequest request){
        goodCollectBiz.updateCollectRemark(request);

    }

}
