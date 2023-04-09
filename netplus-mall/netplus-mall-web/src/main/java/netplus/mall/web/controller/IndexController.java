package netplus.mall.web.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.PreAuth;
import netplus.mall.api.enums.OrderStatusEnum;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/mall")
public class IndexController extends BasedController {

    @Autowired
    IdentityRest identityRest;

    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/basicData")
    public String basicData() {
        return "basicData";
    }

    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/basicData1")
    public String basicData1() {
        return "basicData1";
    }

    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/basicData2")
    public String basicData2() {
        return "basicData2";
    }

    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/basicData3")
    public String basicData3() {
        return "basicData3";
    }
    /*订单管理*/
    @PreAuth(privilegeCode = "PG0102")
    @GetMapping("/orderManage")
    public String permissonSet() {
        return "mall/orderManage";
    }

    /*商城销售管理*/
    /*商品组管理*/
    @PreAuth(privilegeCode = "PG0007")
    @GetMapping("/goodsGroup")
    public String goodsGroup() {return "goodsGroup";}

    /*销售订单*/
    @PreAuth(privilegeCode = "PG0008")
    @GetMapping("/supplier/saleOrder")
    public String supplierSaleOrder(ModelMap modelMap, HttpServletRequest request) {
        String orderStatus = request.getParameter("orderStatus");
        System.out.println(orderStatus);
        if(!StringUtils.isEmpty(orderStatus)){
            modelMap.put("orderStatus", orderStatus);
            String statusName = OrderStatusEnum.getName(orderStatus);
            System.out.println(statusName);
            modelMap.put("orderStatusName", statusName);
        }

        return "mall/saleOrder";
    }

    /*
    * 商品挂牌管理*/
    @PreAuth(privilegeCode = "PG0006")
    @GetMapping("/goodsListed")
    public String goodsListed() {return "goodsListed";}

    /*商品图片管理*/
    @PreAuth(privilegeCode = "PG0005")
    @GetMapping("/goodsPicture")
    public String goodsPicture() {return "goodsPicture";}

    /*商品图片详细图片管理*/
    @PreAuth(privilegeCode = "PG0005")
    @GetMapping("/goodsPictureDetail")
    public String goodsPictureDetail() {return "goodsPictureDetail";}

    /*商品图片回收站管理*/
    @PreAuth(privilegeCode = "PG0005")
    @GetMapping("/goodsPictureRecycleBin")
    public String goodsPictureRecycleBin() {return "goodsPictureRecycleBin";}

    /*采购订单*/
    @PreAuth(privilegeCode = "PG0020")
    @GetMapping("/purchaseOrder")
    public String purchaseOrder(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("status",request.getParameter("status"));
        return "mall/purchaseOrder";
    }

    /*商品历史信息*/
    @PreAuth(privilegeCode = "PG0104")
    @GetMapping("/goodsHistory")
    public String goodsHistory(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("status",request.getParameter("status"));
        return "mall/goodsHistory";
    }

    /*收藏夹*/
    @PreAuth(privilegeCode = "PG0021")
    @GetMapping("/favorites")
    public String favorites() {
        return "mall/favorites";
    }

    /* 商品挂牌审核*/
    @PreAuth(privilegeCode = "PG0022")
    @GetMapping("/goodsAudit")
    public String goodsAudit(ModelMap modelMap,HttpServletRequest request) {
        modelMap.put("status", request.getParameter("status"));
        return "goodsAudit";
    }

    /*我已审核*/
    @PreAuth(privilegeCode = "PG0022")
    @GetMapping("/myGoodsAudit")
    public String myGoodsAudit() {
        return "myGoodsAudit";
    }


    /* （组长）商品挂牌审核*/
    @PreAuth(privilegeCode = "PG0042")
    @GetMapping("/groupGoodsAudit")
    public String groupGoodsAudit(ModelMap modelMap,HttpServletRequest request) {
        modelMap.put("status", request.getParameter("status"));
        return "groupGoodsAudit";
    }

    /*（组长）我已审核*/
    @PreAuth(privilegeCode = "PG0042")
    @GetMapping("/groupMyGoodsAudit")
    public String groupMyGoodsAudit() {
        return "groupMyGoodsAudit";
    }


    /*商品修改审核*/
    @PreAuth(privilegeCode = "PG0044")
    @GetMapping("/goodsUpdate")
    public String goodsUpdate() {
        return "goodsUpdate";
    }

    /*修改我已审核*/
    @PreAuth(privilegeCode = "PG0044")
    @GetMapping("/myGoodsUpdate")
    public String myGoodsUpdate() {
        return "myGoodsUpdate";
    }

    /*（组长)商品修改审核*/
    @PreAuth(privilegeCode = "PG0045")
    @GetMapping("/groupGoodsUpdate")
    public String groupGoodsUpdate() {
        return "groupGoodsUpdate";
    }

    /*（组长）修改我已审核*/
    @PreAuth(privilegeCode = "PG0045")
    @GetMapping("/groupMyGoodsUpdate")
    public String groupMyGoodsUpdate() {
        return "groupMyGoodsUpdate";
    }

    /*商品挂牌查询*/
    @PreAuth(privilegeCode = "PG0023")
    @GetMapping("/goodsSearch")
    public String goodsSearch() { return "goodsSearch";}

    /*商城购买区*/
    @GetMapping("/shoppingFilter")
    public String shoppingFilter(ModelMap modelMap, HttpServletRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        modelMap.put("twolevelclaname", request.getParameter("twolevelclaname"));
        modelMap.put("twolevelclapk", request.getParameter("twolevelclapk"));
        modelMap.put("searchKey", request.getParameter("searchKey"));
        modelMap.put("categoryname", request.getParameter("categoryname"));
        modelMap.put("categorypk", request.getParameter("categorypk"));
        modelMap.put("onelevelclaname", request.getParameter("onelevelclaname"));
        modelMap.put("onelevelclapk", request.getParameter("onelevelclapk"));
        modelMap.put("productname",request.getParameter("productname"));
        if (loginUserBean.getRole().equals("S")) {
            return "redirect:/";
        }
        return "mall/shoppingFilter";
    }

    /*商品详情*/
    @GetMapping("/goodsDetail")
    public String goodsDetail(ModelMap modelMap, HttpServletRequest request) {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        if (loginUserBean.getRole().equals("S")) {
            return "redirect:/";
        }

        modelMap.put("goodsId",request.getParameter("goodsId"));
        return "mall/goodsDetail";
    }


    /*商品详情预览*/
    @GetMapping("/goodPreview")
    public String goodPreview(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("goodsId",request.getParameter("goodsId"));
        return "goodPreview";
    }

    /*购物车*/
    @GetMapping("/shoppingCart")
    public String shoppingCart() {return "mall/shoppingCart";}

    /*结算*/
    @PostMapping("/checkout")
    public String checkout(ModelMap modelMap, @RequestParam String goodIdList) {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        modelMap.put("companyName", loginUserBean.getCompanyName());
        modelMap.put("deptName", loginUserBean.getDeptName());
        modelMap.put("deptNo", loginUserBean.getDeptNo());
        modelMap.put("username", loginUserBean.getUsername());
        modelMap.put("goodIdList", goodIdList);

        return "mall/checkout";
    }

    /*采购订单详情*/
    @PreAuth(privilegeCode = "PG0020")
    @GetMapping("/purchaseOrderDetail/{orderNo}")
    public String purchaseOrderDetail(@PathVariable(name="orderNo") String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        return "mall/purchaseOrderDetail";
    }

    /*销售订单详情*/
    @PreAuth(privilegeCode = "PG0008")
    @GetMapping("/saleOrderDetail/{orderNo}")
    public String saleOrderDetail(@PathVariable(name="orderNo") String orderNo, Model model) {
        model.addAttribute("orderNo", orderNo);
        return "mall/saleOrderDetail";
    }


    @GetMapping("/goodEdit/{goodId}")
    public String goodEdit(@PathVariable(name="goodId") String goodId, Model model) {
        model.addAttribute("goodId", goodId);
        return "goodEdit";
    }

    @PreAuth(privilegeCode = "PG0100")
    @GetMapping("/commodityHarvest")
    public String goodEdit() {
        return "commodityHarvest";
    }


    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/advertising")
    public String advertising() {
        return "advertising";
    }

    @PreAuth(privilegeCode = "PG0024")
    @GetMapping("/basicData4")
    public String basicData4() {
        return "basicData4";
    }

    @PreAuth(privilegeCode = "PG0103")
    @GetMapping("/goodsInventory")
    public String goodsInventory() {
        return "mall/goodsInventory";
    }

    @PreAuth(privilegeCode = "PG0110")
    @GetMapping("/expiringContracts")
    public String expiringContracts() {
        return "statisticalWorkbench/expiringContracts";
    }

    @PreAuth(privilegeCode = "PG0110")
    @GetMapping("/pendingDelivery")
    public String pendingDelivery() {
        return "statisticalWorkbench/pendingDelivery";
    }

    @PreAuth(privilegeCode = "PG0110")
    @GetMapping("/pendingOrders")
    public String pendingOrders() {
        return "statisticalWorkbench/pendingOrders";
    }

    @PreAuth(privilegeCode = "PG0110")
    @GetMapping("/unlistedGoods")
    public String unlistedGoods() {
        return "statisticalWorkbench/unlistedGoods";
    }



}
