package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.request.mall.GetGoodsHistoryRequest;
import netplus.mall.api.request.mall.SearchRequest;
import netplus.mall.api.vo.GoodsHistoryVo;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.category.CategoryVo;
import netplus.mall.service.biz.MallBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MallController extends BasedController {

    @Autowired
    private MallBiz mallBiz;

    @PostMapping(Urls.Mall.search)
    private PageBean<GoodsVo> search(@RequestBody SearchRequest request) {
        return mallBiz.search(request);
    }

    @Anonymous
    @PostMapping(Urls.Mall.getAllCategory)
    private List<CategoryVo> getAllCategory() {
        return mallBiz.getAllCategory();
    }

    @PostMapping(Urls.Mall.getGoodsHistory)
    private PageBean<GoodsHistoryVo> getGoodsHistory (@RequestBody GetGoodsHistoryRequest request) {
        return mallBiz.getGoodsHistory(request);
    }
}
