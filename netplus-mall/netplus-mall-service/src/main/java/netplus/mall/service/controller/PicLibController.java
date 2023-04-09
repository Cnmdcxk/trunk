package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.request.basicData.InitBasicPicRequest;
import netplus.mall.api.request.picLib.GetPicByMatrlTmAndSupplierNoRequest;
import netplus.mall.api.request.picLib.GetPicLibListRequest;
import netplus.mall.api.rest.PicLibRest;
import netplus.mall.api.vo.Tbmqq407Vo;
import netplus.mall.api.vo.picLib.*;
import netplus.mall.service.biz.PicLibBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PicLibController extends BasedController implements PicLibRest {

    @Autowired
    private PicLibBiz picLibBiz;


    @PostMapping(Urls.PicLib.getPicLibList)
    public List<PicLibVo> getPicLibList(@RequestBody GetPicLibListRequest request) {
        return picLibBiz.getPicLibList(request);
    }

    @PostMapping(Urls.PicLib.updatePicLib)
    public void updatePicLib(@RequestBody UpdatePicLibRequest request) {
         picLibBiz.updatePicLib(request);
    }

    @PostMapping(Urls.PicLib.delPicLib)
    public void delPicLib(@RequestBody DelPicLibRequest request) {
         picLibBiz.delPicLib(request);
    }

    @PostMapping(Urls.PicLib.createPicLib)
    public ApiResponse<String> createPicLib(@RequestBody CreatePicLibRequest request) {
        return picLibBiz.createPicLib(request);
    }

    @PostMapping(Urls.PicLib.initBasicPic)
    public void initBasicPic(@RequestBody InitBasicPicRequest request) {
        picLibBiz.initBasicPic(request);
    }


    @Anonymous
    @PostMapping(Urls.PicLib.getPicByMatrlTmAndSupplierNo)
    public List<Tbmqq429Vo> getPicByMatrlTmAndSupplierNo(@RequestBody GetPicByMatrlTmAndSupplierNoRequest request) {
        return picLibBiz.getPicByMatrlTmAndSupplierNo(request);
    }

    @Anonymous
    @PostMapping(Urls.PicLib.getPicByMatrlTm)
    public List<Tbmqq407Vo> getPicByMatrlTm(@RequestBody List<String> request) {
        return picLibBiz.getPicByMatrlTm(request);
    }
}
