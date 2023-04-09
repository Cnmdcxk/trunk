package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.response.out.JK0005.JK0005SubResponse;
import netplus.joint.erp.api.response.out.JK0006.JK0006SubResponse;
import netplus.joint.erp.api.response.out.JK0010.JK0010SubResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.request.mall.GetDeviceRequest;
import netplus.mall.api.request.mall.GetProjectRequest;
import netplus.mall.api.request.order.AddShoppingCartFromOrderRequest;
import netplus.mall.api.request.shoppingCart.*;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartRequest;
import netplus.mall.api.rest.ShoppingCartRest;
import netplus.mall.api.vo.ShoppingCartVo;
import netplus.mall.api.vo.shoppingCart.ImportShoppingCartResultVo;
import netplus.mall.service.biz.IfaceBiz;
import netplus.mall.service.biz.ShoppingCartBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ShoppingCartController extends BasedController implements ShoppingCartRest {

    @Autowired
    private ShoppingCartBiz shoppingCartBiz;

    @Autowired
    private IfaceBiz ifaceBiz;


    @PostMapping(Urls.ShoppingCart.getShoppingCartCount)
    public int getShoppingCartCount() {
        return shoppingCartBiz.getShoppingCartCount();
    }


    @PostMapping(Urls.ShoppingCart.addShoppingCart)
    public void addShoppingCart(@RequestBody AddShoppingCartRequest request) {
        shoppingCartBiz.addShoppingCart(request);
    }

    @Anonymous
    @PostMapping(Urls.ShoppingCart.erpAddShoppingCart)
    public ApiResponse erpAddShoppingCart(@RequestBody ErpAddShoppingCartRequest request) {
        return shoppingCartBiz.erpAddShoppingCart(request);
    }

    @PostMapping(Urls.ShoppingCart.getMyShoppingCartList)
    public PageBean<ShoppingCartVo> getMyShoppingCartList (@RequestBody GetMyShoppingCartRequest request) {
        return shoppingCartBiz.getMyShoppingCartList(request);
    }

    @PostMapping(Urls.ShoppingCart.exportMyShoppingCartList)
    public String exportMyShoppingCartList (@RequestBody GetMyShoppingCartRequest request) {
        return shoppingCartBiz.exportMyShoppingCartList(request);
    }

    @PostMapping(Urls.ShoppingCart.delMyShoppingCart)
    public void delMyShoppingCart(@RequestBody DelMyShoppingCartRequest request) {
        shoppingCartBiz.delMyShoppingCart(request);
    }


    @PostMapping(Urls.ShoppingCart.importShoppingCart)
    public ApiResponse<ImportShoppingCartResultVo> importShoppingCart(@RequestBody ImportShoppingCartRequest request) {
        return shoppingCartBiz.importShoppingCart(request);
    }


    @PostMapping(Urls.ShoppingCart.exportErrInfo)
    public String exportErrInfo(@RequestBody ExportErrInfoRequest request) throws Exception {
        return shoppingCartBiz.exportErrInfo(request);
    }

    @PostMapping(Urls.ShoppingCart.addShoppingCartFromOrder)
    public ApiResponse addShoppingCartFromOrder(@RequestBody AddShoppingCartFromOrderRequest request) throws Exception {
        return shoppingCartBiz.addShoppingCartFromOrder(request);
    }


    @PostMapping(Urls.ShoppingCart.updateProject)
    public void updateProject(@RequestBody UpdateShoppingCartRequest request) throws Exception {
        shoppingCartBiz.updateProject(request);
    }


    @PostMapping(Urls.ShoppingCart.batchUpdateProject)
    public void batchUpdateProject(@RequestBody BatchUpdateProjectRequest request) throws Exception {
        shoppingCartBiz.batchUpdateProject(request);
    }

    @PostMapping(Urls.ShoppingCart.updateDevice)
    public void updateDevice(@RequestBody UpdateShoppingCartRequest request) throws Exception {
        shoppingCartBiz.updateDevice(request);
    }

    @PostMapping(Urls.ShoppingCart.updatePic)
    public void updatePic(@RequestBody UpdateShoppingCartRequest request) throws Exception {
        shoppingCartBiz.updatePic(request);
    }


    @PostMapping(Urls.ShoppingCart.getProject)
    public PageBean<JK0005SubResponse> getProject(@RequestBody GetProjectRequest request) throws Exception {
        return ifaceBiz.getProject(request);
    }


    @PostMapping(Urls.ShoppingCart.getDevice)
    public PageBean<JK0006SubResponse> getDevice(@RequestBody GetDeviceRequest request) throws Exception {
        return ifaceBiz.getDevice(request);
    }


    @PostMapping(Urls.ShoppingCart.checkQty)
    public Map<String, JK0010SubResponse> checkQty(@RequestBody CheckQtyRequest request) throws Exception {
        return shoppingCartBiz.checkQty(request);
    }

    @PostMapping(Urls.ShoppingCart.updateShoppingCartRemark)
    public void updateShoppingCartRemark(@RequestBody UpdateShoppingCartRemarkRequest request){
        shoppingCartBiz.updateShoppingCartRemark(request);

    }

    @PostMapping(Urls.ShoppingCart.updateDeviceSimpleNo)
    public void updateDeviceSimpleNo(@RequestBody UpdateShoppingCartRequest request) throws Exception {
        shoppingCartBiz.updateDeviceSimpleNo(request);
    }


}
