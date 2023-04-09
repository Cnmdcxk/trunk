package netplus.mall.service.controller;

import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.request.goodsInventory.GoodsInventoryPageRequest;
import netplus.mall.api.request.goodsInventory.MallGoodsInventoryPageRequest;
import netplus.mall.api.vo.goodsInventory.GoodsInventoryPageVo;
import netplus.mall.api.vo.goodsInventory.MallGoodsInventoryPageVo;
import netplus.mall.service.biz.GoodsInventoryBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsInventoryController {

    @Autowired
    GoodsInventoryBiz goodsInventoryBiz;

    @Anonymous
    @PostMapping(Urls.GoodsInventory.getPage)
    public PageBean<GoodsInventoryPageVo> getPage(@RequestBody GoodsInventoryPageRequest request){
        return goodsInventoryBiz.getPage(request);
    }

    @Anonymous
    @PostMapping(Urls.GoodsInventory.getMallGoodsInventoryDetailPage)
    public PageBean<MallGoodsInventoryPageVo> getMallGoodsInventoryDetailPage(@RequestBody MallGoodsInventoryPageRequest request){
        return goodsInventoryBiz.getMallGoodsInventoryDetailPage(request);
    }

}
