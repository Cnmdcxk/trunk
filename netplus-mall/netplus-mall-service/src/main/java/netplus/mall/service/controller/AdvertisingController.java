package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.request.advertising.*;
import netplus.mall.api.rest.AdvertisingRest;
import netplus.mall.service.biz.AdvertisingBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertisingController extends BasedController implements AdvertisingRest {

    @Autowired
    AdvertisingBiz advertisingBiz;

    @PostMapping(Urls.Advertising.listAdvertising)
    public ApiResponse listAdvertising(){
        return advertisingBiz.listAdvertising();
    }

    @PostMapping(Urls.Advertising.getPublishAdvertisingCount)
    public ApiResponse getPublishAdvertisingCount(){
        return advertisingBiz.getPublishAdvertisingCount();
    }

    @PostMapping(Urls.Advertising.addAdvertising)
    public ApiResponse addAdvertising(@RequestBody AddAdvertisingRequest request){
        return advertisingBiz.addAdvertising(request);
    }

    @PostMapping(Urls.Advertising.publishAdvertising)
    public ApiResponse publishAdvertising(@RequestBody ChangeAdvertisingStatusRequest request){
        return advertisingBiz.publishAdvertising(request);
    }

    @PostMapping(Urls.Advertising.cancelPublishAdvertising)
    public ApiResponse cancelPublishAdvertising(@RequestBody ChangeAdvertisingStatusRequest request){
        return advertisingBiz.cancelPublishAdvertising(request);
    }

    @PostMapping(Urls.Advertising.removeAdvertising)
    public ApiResponse removeAdvertising(@RequestBody RemoveAdvertisingRequest request){
        return advertisingBiz.removeAdvertising(request);
    }

    @PostMapping(Urls.Advertising.advertisingLeftMoveOne)
    public ApiResponse advertisingLeftMoveOne(@RequestBody MoveAdvertisingRequest request){
        return advertisingBiz.advertisingLeftMoveOne(request);
    }

    @PostMapping(Urls.Advertising.advertisingRightMoveOne)
    public ApiResponse advertisingRightMoveOne(@RequestBody MoveAdvertisingRequest request){
        return advertisingBiz.advertisingRightMoveOne(request);
    }

    @Anonymous
    @Override
    public ApiResponse getPublishAdvertising(){
        return advertisingBiz.getPublishAdvertising();
    }
}
