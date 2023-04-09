package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.request.*;
import netplus.mall.api.request.goodGroup.CreateGroupRequest;
import netplus.mall.api.vo.GoodsGroupVo;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.biz.GoodsGroupBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 商品组数据
 * @author: yxw
 * @date: 2021/6/17 15:21
 */
@RestController
public class GoodsGroupController extends BasedController {

    @Autowired
    GoodsGroupBiz goodsGroupBiz;

    /*查询商品组*/
    @PostMapping(Urls.GoodsGroup.getGoodsGroupList)
    public PageBean<GoodsGroupVo> getGoodsGroupList(@RequestBody GetGoodsGroupListRequest request) {
        return goodsGroupBiz.getGoodsGroupList(request);
    }

    /*查询商品组*/
    @PostMapping(Urls.GoodsGroup.getGroupGoodList)
    public List<GoodsVo> getGroupGoodList(@RequestBody GetGoodsListedRequest request) {
        return goodsGroupBiz.getGroupGoodList(request);
    }

    /*删除商品组*/
    @PostMapping(Urls.GoodsGroup.deleteGoodsGroup)
    public void deleteGoodsGroup(@RequestBody DeleteGoodsRequest request) {
         goodsGroupBiz.deleteGoodsGroup(request);
    }

    /*删除商品组中商品*/
    @PostMapping(Urls.GoodsGroup.deleteGoodsFromGroup)
    public void deleteGoodsFromGroup(@RequestBody DeleteGoodsRequest request) {
        goodsGroupBiz.deleteGoodsFromGroup(request);
    }

    /*批量删除商品组中商品*/
    @PostMapping(Urls.GoodsGroup.batchDeleteGoods)
    public void batchDeleteGoods(@RequestBody BatchDeleteGoodsRequest request) {
        goodsGroupBiz.batchDeleteGoods(request);
    }

    @PostMapping(Urls.GoodsGroup.createGroup)
    public void createGroup(@RequestBody CreateGroupRequest request) {
        request.setAppId(getAppID());
        goodsGroupBiz.createGroup(request);
    }


}
