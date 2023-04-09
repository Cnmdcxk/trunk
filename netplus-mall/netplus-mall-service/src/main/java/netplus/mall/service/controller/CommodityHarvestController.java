package netplus.mall.service.controller;


import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.pojo.ygmalluser.Tbmqq439;
import netplus.mall.api.request.HarvesterManagement.CommodityHarvestRequest;
import netplus.mall.service.biz.CommodityHarvestBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommodityHarvestController {
    @Autowired
    CommodityHarvestBiz commodityHarvestBiz;

    /**
     * 获取所有收货地址数据
     * @param request
     * @return
     */
    @PostMapping(Urls.commodityHarves)
    public PageBean<Tbmqq439> getCommodityInformation(@RequestBody CommodityHarvestRequest request){
        PageBean<Tbmqq439> allList = commodityHarvestBiz.getAllList(request);
        return allList;
    }

    /**
     * 添加收货地址数据
     * @param request
     * @return
     */
    @Anonymous
    @PostMapping(Urls.addInformation)
    public ApiResponse addInformation(@RequestBody CommodityHarvestRequest request){
        return commodityHarvestBiz.addInformation(request);
    }

    /**
     * 修改收货地址数据
     * @param request
     * @return
     */
    @Anonymous
    @PostMapping(Urls.updateInformation)
    public ApiResponse updateInformation(@RequestBody CommodityHarvestRequest request){
        return commodityHarvestBiz.updateInformation(request);
    }


    /**
     * 删除收货地址数据
     * @param request
     * @return
     */
    @Anonymous
    @PostMapping(Urls.deleteByCode)
    public ApiResponse deleteByCode(@RequestBody CommodityHarvestRequest request){
        return commodityHarvestBiz.deleteByCode(request);
    }
}
