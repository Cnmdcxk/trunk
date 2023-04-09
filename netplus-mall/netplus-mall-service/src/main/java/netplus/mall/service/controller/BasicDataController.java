package netplus.mall.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.response.out.JK0031.JK0031SubResponse;
import netplus.joint.erp.api.response.out.JK0033.JK0033SubResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.request.*;
import netplus.mall.api.request.basicData.*;
import netplus.mall.api.rest.BasicDataRest;
import netplus.mall.api.vo.*;
import netplus.mall.api.vo.basicData.CheckOrderConfigVo;
import netplus.mall.api.vo.picLib.CreatePicLibRequest;
import netplus.mall.service.biz.BasicDataBiz;
import netplus.mall.service.biz.PicLibBiz;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商城基础数据管理
 * @author: wangyx
 * @date: 2021/6/9 15:21
 */
@Api(tags="技术数据管理")
@RestController
public class BasicDataController<enterpriseNameArr> extends BasedController implements BasicDataRest {
    @Autowired
    BasicDataBiz basicDataBiz;

    @Autowired
    PicLibBiz picLibBiz;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @PostMapping(Urls.BasicData.getClassifyList)
    public PageBean<ClassifyVo> getClassifyList(@RequestBody GetBasicDataClassifyListRequest request) {
        return basicDataBiz.getClassifyList(request);
    }

    @PostMapping(Urls.BasicData.addClassifyData)
    public ApiResponse addClassifyData(@RequestBody AddBasicDataClassifyRequest request) {
        return basicDataBiz.addClassifyData(request);
    }

    @PostMapping(Urls.BasicData.modifyClassifyData)
    public ApiResponse modifyClassifyData(@RequestBody ModifyBasicDataClassifyRequest request) {
        return basicDataBiz.modifyClassifyData(request);
    }

    @PostMapping(Urls.BasicData.getMaterialList)
    public PageBean<ClassifyVo> getMaterialList(@RequestBody GetBasicDataMaterialListRequest request) {
        return basicDataBiz.getMaterialList(request);
    }

    @PostMapping(Urls.BasicData.getSkuMaterialList)
    public PageBean<ClassifyVo> getSkuMaterialList(@RequestBody GetBasicDataMaterialListRequest request) {
        return basicDataBiz.getSkuMaterialList(request);
    }

    @PostMapping(Urls.BasicData.modifyMaterialData)
    public ApiResponse modifyMaterialData(@RequestBody ModifyBasicDataMaterialRequest request) {
        return basicDataBiz.modifyMaterialData(request);
    }



    @PostMapping(Urls.BasicData.importErpCla)
    public void importErpCla(@RequestBody ImportErpClaRequest request) {
        basicDataBiz.importErpCla(request);
    }

    @PostMapping(Urls.BasicData.importTwoLevelCla)
    public void importTwoLevelCla(@RequestBody ImportTwoLevelClaNameRequest request) {
        basicDataBiz.importTwoLevelCla(request);
    }

    @PostMapping(Urls.BasicData.importThrplatProduct)
    public void importThrplatProduct(@RequestBody ImportThrplatProductRequest request) {
        basicDataBiz.importThrplatProduct(request);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.BasicData.getCategoryByGoodsno)
    public CategoryVo getCategoryByGoodsno(@RequestBody String skuId) {
        return basicDataBiz.getCategoryByGoodsno(skuId);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.BasicData.getAllMatrlList)
    public List<Tbmqq405Vo> getAllMatrlList() {
        return basicDataBiz.getAllMatrlList();
    }


    @Override
    @Anonymous
    @PostMapping(Urls.BasicData.getMaterialInfo)
    public List<Tbmqq405Vo> getMaterialInfo(@RequestBody GetMaterialInfoRequest request) {
        return basicDataBiz.getMaterialInfo(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getCategoryNameList)
    public List<String> getCategoryNameList (@RequestBody GetCategoryNameListRequest request) {
        return basicDataBiz.getCategoryNameList(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getOneLevelClaNameList)
    public List<String> getOneLevelClaNameList (@RequestBody GetCategoryNameListRequest request) {
        return basicDataBiz.getOneLevelClaNameList(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getTwoLevelClaNameList)
    public List<String> getTwoLevelClaNameList (@RequestBody GetCategoryNameListRequest request) {
        return basicDataBiz.getTwoLevelClaNameList(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.exportBasicDataClassifyList)
    public String getTwoLevelClaNameList (@RequestBody GetBasicDataClassifyListRequest request) throws IOException {
        return basicDataBiz.exportClassifyData(request);
    }



    @Anonymous
    @PostMapping(Urls.BasicData.getMatrlNoByGoodNo)
    public Tbmqq406Vo getMatrlNoByGoodNo (@RequestBody GetMatrlNoRequest request) {
        return basicDataBiz.getMatrlNoByGoodNo(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.exportExcel)
    public String  exportExcel (@RequestBody GetBasicDataMaterialListRequest request) throws IOException {
        return basicDataBiz.exportThrplatData(request);
    }


    @PostMapping(Urls.BasicData.getBasicPicLib)
    public PageBean<Tbmqq407Vo> getBasicPicLib(@RequestBody GetBasicDataMaterialListRequest  request) {
        return basicDataBiz.getBasicPicLib(request);
    }

    @PostMapping(Urls.BasicData.createBasicPicLib)
    public ApiResponse CreateBasicPicLib(@RequestBody @Validated(CreatePicLibRequest.add.class) CreatePicLibRequest  request) {
         return basicDataBiz.createBasicPicLib(request);
    }

    @PostMapping(Urls.BasicData.delBasicPic)
    public void delBasicPic(@RequestBody DelBasicPicRequest request) {
        basicDataBiz.delBasicPic(request);
    }



    @Anonymous
    @PostMapping(Urls.BasicData.syncBasicData)
    public void syncBasicData() {
        basicDataBiz.syncBasicData();
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getMatrlByIdsAndSupplierNo)
    public List<Tbmqq405Vo> getMatrlByIdsAndSupplierNo(@RequestBody GetMatrlByIdsAndSupplierNoRequest request) {
        return basicDataBiz.getMatrlByIdsAndSupplierNo(request);
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getCheckOrderConfig)
    public CheckOrderConfigVo getCheckOrderConfig(){
        return basicDataBiz.getCheckOrderConfig();
    }

    @Anonymous
    @PostMapping(Urls.BasicData.getCostDept)
    public List<JK0031SubResponse> getCostDept(){
        return basicDataBiz.getCostDept();
    }

    @PostMapping(Urls.BasicData.getMatrlQualityByMatrlId)
    public List<JK0033SubResponse> getMatrlQualityByMatrlId(@RequestBody GetMatrlQualityByMatrlIdRequest request){
        return basicDataBiz.getMatrlQualityByMatrlId(request);
    }
}
