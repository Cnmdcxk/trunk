package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.mall.api.Urls;
import netplus.mall.api.request.good.GetSupplierGoodEditInfoRequest;
import netplus.mall.api.request.good.SaveGoodInfoRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.biz.GoodsEditBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsEditController extends BasedController {

    @Autowired
    GoodsEditBiz goodsEditBiz;

    @PostMapping(Urls.GoodsEdit.getSupplierGoodEditInfo)
    public GoodsVo getSupplierGoodEditInfo (@RequestBody GetSupplierGoodEditInfoRequest request){
        return goodsEditBiz.getSupplierGoodEditInfo(request);
    }

    @PostMapping(Urls.GoodsEdit.save)
    public void save(@RequestBody SaveGoodInfoRequest request){
        goodsEditBiz.save(request);
    }



}
