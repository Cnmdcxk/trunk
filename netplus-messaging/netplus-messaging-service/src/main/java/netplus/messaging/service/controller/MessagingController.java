package netplus.messaging.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.messaging.api.Urls;
import netplus.messaging.api.request.MessagingRequest;
import netplus.messaging.api.request.SendMsgRequest;
import netplus.messaging.api.request.UpdateMessagingStatusRequest;
import netplus.messaging.api.rest.MessagingRest;
import netplus.messaging.api.vo.MessagingVo;
import netplus.messaging.api.vo.NumCountVo;
import netplus.messaging.service.biz.MessagingBiz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "消息接口")
@RestController
public class MessagingController extends BasedController implements MessagingRest {

    private static Log logger = LogFactory.getLog(MessagingController.class);

    @Autowired
    private MessagingBiz messagingBiz;

    @Anonymous
    @PostMapping(Urls.Messaging.query)
    @Override
    public PageBean<MessagingVo> query(@RequestBody MessagingRequest messagingRequest){
        return messagingBiz.query(messagingRequest);
    }

    @Anonymous
    @PostMapping(Urls.Messaging.getAllCount)
    public NumCountVo getAllCount(){
        return messagingBiz.getAllCount();
    }

    @Anonymous
    @PostMapping(Urls.Messaging.getMyMsgGroupList)
    public List<KeyValue> getMyMsgGroupList(){
        return messagingBiz.getMyMsgGroupList();
    }

    @Anonymous
    @PostMapping(Urls.Messaging.getCountWithGroup)
    public Map<String,String> getCountWithGroup(){
        return messagingBiz.getCountWithGroup();
    }

    @Anonymous
    @PostMapping(Urls.Messaging.updateState)
    public int updateState(@RequestBody UpdateMessagingStatusRequest request){
        return messagingBiz.updateState(request.getMessagingId());
    }

    @PostMapping(Urls.Messaging.updateAllState)
    public int updateAllState(){
        return messagingBiz.updateAllState();
    }

    @Anonymous
    @PostMapping(Urls.Messaging.sendMsg)
    @Override
    public void sendMsg(@RequestBody List<SendMsgRequest> request){
        messagingBiz.sendMsg(request);
    }
}
