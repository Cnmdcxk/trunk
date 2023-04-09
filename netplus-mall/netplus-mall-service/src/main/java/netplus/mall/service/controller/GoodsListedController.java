package netplus.mall.service.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.mall.api.Urls;
import netplus.mall.api.pojo.ygmalluser.Tbmqq437;
import netplus.mall.api.request.GetGoodsDetailRequest;
import netplus.mall.api.request.GetGoodsListedRequest;
import netplus.mall.api.request.good.*;
import netplus.mall.api.rest.GoodsRest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.IndexGoodsVo;
import netplus.mall.api.vo.GoodsAuditExcelOutVo;
import netplus.mall.service.biz.CreateGoodBiz;
import netplus.mall.service.biz.GoodViewHistoryBiz;
import netplus.mall.service.biz.GoodsListedBiz;
import netplus.mall.service.biz.ZkhGoodBiz;
import netplus.mall.service.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商城数据
 * @author: yxw
 * @date: 2021/6/17 15:21
 */
@RestController
@Slf4j
public class GoodsListedController extends BasedController implements GoodsRest {

    @Autowired
    GoodsListedBiz goodsListedBiz;

    @Autowired
    GoodViewHistoryBiz goodViewHistoryBiz;

    @Autowired
    CreateGoodBiz createGoodBiz;

    @Autowired
    ZkhGoodBiz zkhGoodBiz;

    /*查询商品*/
    @PostMapping(Urls.GoodsListed.getGoodsListedList)
    public PageBean<GoodsVo> getGoodsListedList(@RequestBody GetGoodsListedRequest request) {
        return goodsListedBiz.getGoodsListedList(request);
    }


    @PostMapping(Urls.GoodsListed.getTabCount)
    public Map<String, Object> getTabCount(@RequestBody GetGoodsListedRequest request) {
        return goodsListedBiz.getTabCount(request);
    }

    @PostMapping(Urls.GoodsListed.getShelfCount)
    public Map<String, Object> getShelfCount(@RequestBody GetGoodsListedRequest request) {
        return goodsListedBiz.getShelfCount(request);
    }
    /*查询首页商品*/
    @Anonymous
    @PostMapping(Urls.GoodsListed.getIndexGoodsList)
    public List<IndexGoodsVo> getIndexGoodsList(@RequestBody GetGoodsListedRequest request) {
        return goodsListedBiz.getIndexGoodsList(request);
    }


    /*查询商品详情*/
    @PostMapping(Urls.GoodsListed.getGoodsDetail)
    public List<GoodsVo> getGoodsDetail(@RequestBody GetGoodsDetailRequest request) {
        return goodsListedBiz.getGoodsDetail(request);
    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.getSiteTop)
    public List<GoodsVo> getSiteTop(@RequestBody GetGoodsListedRequest request) {
        return goodsListedBiz.getSiteTop(request);
    }


    @PostMapping(Urls.GoodsListed.batchUpdatePrice)
    public void batchUpdatePrice(@RequestBody BatchUpdatePriceRequest request) {
        goodsListedBiz.batchUpdatePrice(request);
    }


    @PostMapping(Urls.GoodsListed.getImportGoodTemp)
    public String getImportGoodTemp(@RequestBody GetGoodsListedRequest request) throws Exception {
        return goodsListedBiz.getImportGoodTemp(request);
    }


    @PostMapping(Urls.GoodsListed.importGoodInfo)
    public void getImportGoodTemp(@RequestBody ImportGoodInfoRequest request) throws Exception {
        goodsListedBiz.importGoodInfo(request);
    }

    @PostMapping(Urls.GoodsListed.batchApproval)
    public void batchApproval(@RequestBody BatchApprovalRequest request) throws Exception {
        goodsListedBiz.batchApproval(request);
    }

    @PostMapping(Urls.GoodsListed.batchMatchPic)
    public void batchMatchPic(@RequestBody BatchMatchPicRequest request) throws Exception {
        goodsListedBiz.batchMatchPic(request);
    }

    @PostMapping(Urls.GoodsListed.getUserViewHistory)
    public List<Tbmqq437> getUserViewHistory() {
        return goodViewHistoryBiz.getUserViewHistory();
    }

    @PostMapping(Urls.GoodsListed.addViewHistory)
    public void addViewHistory(@RequestBody AddViewHistoryRequest request) {
        goodViewHistoryBiz.addViewHistory(request.getGoodId());
    }

    @Anonymous
    @PostMapping(Urls.GoodsListed.batchInsert)
    public void batchInsert(@RequestBody BatchInsertGoodRequest request) {
        createGoodBiz.batchInsert(request);
    }

    //导入excel表
    @Anonymous
    @PostMapping(Urls.GoodsListed.exportGoodsListedList)
    public String exportGoodsList(@RequestBody GetGoodsListedRequest request) throws Exception {
        return goodsListedBiz.exportGoodsList(request);
    };


    @Anonymous
    @PostMapping(Urls.GoodsListed.exportNewGoodsListedList)
    public void exportNewGoodsList(HttpServletResponse response, String json) throws Exception {

        goodsListedBiz.exportNewGoodsList(response,json);
    };


    @ApiOperation(value = "测试导出")
    @Anonymous
    @GetMapping(Urls.GoodsListed.export)
    public void testExcelOut(HttpServletResponse response) throws Exception {

        ExcelUtil.build()
                .setData( page -> {
                    if (page > 100) {
                        return null;
                    }

                    List<GoodsAuditExcelOutVo> data = new ArrayList<>();
                    for (int i = (page-1)*100 ;i<100*page ;i++) {
                        GoodsAuditExcelOutVo goodsAuditExcelOutVo = new GoodsAuditExcelOutVo();
                        goodsAuditExcelOutVo.setGoodno(i+"");
                        goodsAuditExcelOutVo.setAgent(i + "str");
                        data.add(goodsAuditExcelOutVo);
                    }
                    return data;
                })
                .setHead(GoodsAuditExcelOutVo.class)
                .setResponse(response)
                .setName("测试导出")
                .write();

    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.exportCheckedGoodsListedList)
    public String exportGoodsListChecked(@RequestBody GetGoodsListedRequest request) throws Exception {
        return goodsListedBiz.exportGoodsListChecked(request);
    };


    @Anonymous
    @PostMapping(Urls.GoodsListed.updateZkhGoodPrice)
    public int updateZkhGoodPrice(@RequestBody UpdateZkhGoodPriceRequest request) {
        return zkhGoodBiz.updateZkhGoodPrice(request);
    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.updateZkhGoodQty)
    public int updateZkhGoodQty(@RequestBody UpdateZkhGoodQtyRequest request) {
        return zkhGoodBiz.updateZkhGoodQty(request);
    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.updateZkhGoodDetail)
    public int updateZkhGoodDetail(@RequestBody UpdateZkhGoodDetailRequest request) {
        return zkhGoodBiz.updateZkhGoodDetail(request);
    }

    @Anonymous
    @PostMapping(Urls.GoodsListed.updateGoodInvalid)
    public int updateGoodInvalid() {
        return goodsListedBiz.updateGoodInvalid();
    }

    @Anonymous
    @PostMapping(Urls.GoodsListed.SetGoodRank)
    public int updateGoodRank() {
        return goodsListedBiz.updateGoodRank();
    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.exportGroupGoodsUpdate)
    public String exportGroupGoodsUpdate(@RequestBody GetGoodsListedRequest request) throws Exception { return goodsListedBiz.exportGroupGoodsUpdate(request);};


    @PostMapping(Urls.GoodsListed.batchUpdateApply)
    public void batchUpdateApply(@RequestBody BatchUpdateApplyRequest request) {
        goodsListedBiz.batchUpdateApply(request);
    };

    @PostMapping(Urls.GoodsListed.batchConfirm)
    public void batchConfirm(@RequestBody BatchAuditRequest request) {
        goodsListedBiz.batchConfirm(request);
    };

    @PostMapping(Urls.GoodsListed.exportConfirm)
    public String exportUpConfirm(@RequestBody GetGoodsListedRequest request) throws Exception {
        return goodsListedBiz.exportConfirm(request);
    };


    @Anonymous
    @PostMapping(Urls.GoodsListed.getGoodBySupplierNo)
    public List<GoodsVo> getGoodBySupplierNo(@RequestBody GetGoodBySupplierNoRequest request) {
        return goodsListedBiz.getGoodBySupplierNo(request);
    };

    @Anonymous
    @PostMapping(Urls.GoodsListed.updateZkhPic)
    public int updateZkhPic(@RequestBody UpdateZkhPicRequest request) {
        return zkhGoodBiz.updateZkhPic(request);
    };


    @Anonymous
    @PostMapping(Urls.GoodsListed.updateZkhPriceAndDetail)
    public int updateZkhPriceAndDetail(@RequestBody UpdateZkhPriceAndDetailRequest request) {
        return zkhGoodBiz.updateZkhPriceAndDetail(request);
    };


    @Anonymous
    @PostMapping(Urls.GoodsListed.priceCompare)
    public List<GoodsVo> priceCompare(@RequestBody GetPriceCompareRequest request) {
        return goodsListedBiz.getPriceCompare(request);
    }


    @Anonymous
    @PostMapping(Urls.GoodsListed.delInvalidGood)
    public int delInvalidGood(){
        return goodsListedBiz.delInvalidGood();
    }








}
