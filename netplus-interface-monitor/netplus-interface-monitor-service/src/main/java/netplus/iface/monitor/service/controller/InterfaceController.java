package netplus.iface.monitor.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.iface.monitor.api.Urls;
import netplus.iface.monitor.api.request.InterFaceRequest;
import netplus.iface.monitor.api.request.InterFaceRespDataRequest;
import netplus.iface.monitor.api.request.RestartReqRequest;
import netplus.iface.monitor.api.vo.Tbmqq460Vo;
import netplus.iface.monitor.service.biz.InterfaceBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//接口管理
@RestController
public class InterfaceController extends BasedController {


    @Autowired
    InterfaceBiz interfaceBiz;

    @PostMapping(Urls.Interfaces.getInterFaceDataList)
        public PageBean<Tbmqq460Vo> getInterFaceDataList(@RequestBody InterFaceRequest request){
        PageBean<Tbmqq460Vo> allList = interfaceBiz.getInterFaceDataList(request);
        return allList;
    }

    @PostMapping(Urls.Interfaces.getRespData)
    public Tbmqq460Vo getRespData(@RequestBody InterFaceRespDataRequest request){
       return interfaceBiz.getRespData(request.getReqid());
    }



    @PostMapping(Urls.Interfaces.restartReq)
    public void restartReq(@RequestBody RestartReqRequest request){
       interfaceBiz.restartReq(request);
    }

    @Anonymous
    @PostMapping(Urls.Interfaces.deleteInterFaceInformation)
    public int deleteInterFaceInformation(){
        return interfaceBiz.deleteInterFaceInformation();
    }
}
